package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.FieldValidator;
import jp.motors.dao.CorporatorDao;
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class AddCorporatorServlet
 */
@WebServlet(urlPatterns={"/add-corporator"}, initParams={@WebInitParam(name="password", value="codetrain123")})
public class AddCorporatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//セッション開始
		HttpSession session = request.getSession(false);
						
		//セッション処理
		OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
		// トップページに移動
			response.sendRedirect("login");
			return;
		}
		
		List<String>  errorMessageList = new ArrayList<>();
		
		//法人アカウント情報の取得
		request.setCharacterEncoding("UTF-8");
		CorporatorDto dto = new CorporatorDto();
		int corporationId = Integer.parseInt(request.getParameter("corporationId"));
		dto.setCorporationId(corporationId);
		String email = request.getParameter("email");
		if (!email.contains("@")) {
			errorMessageList.add("不正メールアドレスです");
		}
		dto.setEmail(email);
		dto.setFirstName(request.getParameter("firstName"));
		dto.setLastName(request.getParameter("lastName"));
		dto.setUpdateOperatorId(operator.getOperatorId());
		dto.setPassword(getInitParameter("password"));
		
		//sessionにcorporationIdを保持
		session.setAttribute("corporationId", corporationId);
		
		
		//入力チェック
		errorMessageList.addAll(FieldValidator.corporatorValidation(dto));
		
		
		//エラーがある場合list-corporatorに遷移
		if (errorMessageList.size() != 0) {
			session.removeAttribute("errorMessageList");
			session.setAttribute("errorMessageList", errorMessageList);	
			session.setAttribute("corporatorDto", dto);
			response.sendRedirect("list-corporator");
			return;
		}
		
		
		//メールアドレスに重複がないか確認
		List<CorporatorDto> corporatorList = new ArrayList<>();
		try (Connection conn = DataSourceManager.getConnection()) {
			CorporatorDao corporatorDao = new CorporatorDao(conn);
			corporatorList = corporatorDao.selectAll();
			
			for(CorporatorDto corporatorLists: corporatorList) {
				
				if ( email.equals(corporatorLists.getEmail())) {
					session.setAttribute("errorMessageList", email + "はすでに存在します");
					response.sendRedirect("list-corporator");
					return;
				}
			}
				
			//会社情報の登録
			corporatorDao.insert(dto);
			session.setAttribute("message", "法人アカウントを登録しました");
				
			//更新メッセージ
				
			//list-corporation
			response.sendRedirect("list-corporator");
					
					
			} catch (SQLException | NamingException e) {
					//エラーを出力表示
					e.printStackTrace();
					//システムエラー画面に遷移する
					response.sendRedirect("operator-system-error.jsp");
					
			}
		
		
	}

}
