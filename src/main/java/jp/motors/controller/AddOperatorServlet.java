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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class AddOperatorServlet
 */
@WebServlet(urlPatterns={"/add-operator"},initParams={@WebInitParam(name="password", value="codetrain123")})
public class AddOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログインページｌに遷移する
				request.getRequestDispatcher("login").forward(request, response);
			
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
		
		List<String> errorMessageList = new ArrayList<>();
		
		// 値取得
		OperatorDto operatorDto = new OperatorDto();
		request.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		operatorDto.setEmail(email);
		operatorDto.setFirstName(request.getParameter("firstName"));
		operatorDto.setLastName(request.getParameter("lastName"));
		int authority = Integer.parseInt(request.getParameter("authority"));
        operatorDto.setAuthority(authority);
        int opratorId = Integer.parseInt(request.getParameter("operatorId"));
		operatorDto.setOperatorId(opratorId);
		operatorDto.setUpdateOperatorId(operator.getOperatorId());
		operatorDto.setPassword(getInitParameter("password"));
		if (!email.contains("@")) {
			errorMessageList.add("不正メールアドレスです");
		}
		// 入力チェック
		errorMessageList.addAll(FieldValidator.operatorValidation(operatorDto));
		//4. エラーがある場合、エラーメッセージをセッションスコープに保持し、ListUserServletにリダイレクトする
		if (errorMessageList.size() != 0) {
			session.removeAttribute("errorMessageList");
			session.setAttribute("errorMessageList", errorMessageList);	
			session.setAttribute("operatorDto", operatorDto);
			response.sendRedirect("operator-list");
			return;
		}
		
		//メールアドレスに重複がないか確認
		List<OperatorDto> operatorList = new ArrayList<>();
		try (Connection conn = DataSourceManager.getConnection()) {
			OperatorDao operatordao = new OperatorDao(conn);
			operatorList = operatordao.selectAll();
			
			for(OperatorDto operatorLists: operatorList) {
				
				if ( email.equals(operatorLists.getEmail())) {
					session.setAttribute("errorMessageList", email + "はすでに存在します");
					response.sendRedirect("operator-list");
					return;
				}
			}
			
			
			//エラーがある場合、エラーメッセージをセッションスコープに保持し、ListUserServletにリダイレクトする
			if (errorMessageList.size() != 0) {
				session.removeAttribute("errorMessageList");
				session.setAttribute("errorMessageList", errorMessageList);	
				response.sendRedirect("operator-list");
				return;
			}
		
			// 登録

			operatordao.insert(operatorDto);
			session.setAttribute("message", "運用者を登録しました");
			//operatorIdをセットしているか確認
			
			//7. ListUserServletにリダイレクト
			response.sendRedirect("operator-list");
			
			
		} catch (SQLException | NamingException e) {
			//エラーを出力表示
			e.printStackTrace();
			//システムエラー画面に遷移する
			response.sendRedirect("operator-system-error.jsp");
			
		}
	}

}
