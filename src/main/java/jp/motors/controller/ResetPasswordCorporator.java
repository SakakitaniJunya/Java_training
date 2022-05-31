package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.CorporatorDao;
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class ResetPasswordCorporator
 */
@WebServlet(urlPatterns={"/reset-password-corporator"},initParams={@WebInitParam(name="password", value="codetrain123")})
public class ResetPasswordCorporator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login");
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
		
		//値取得
		request.setCharacterEncoding("UTF-8");
		CorporatorDto corporatorDto = new CorporatorDto();
		int corporationId = Integer.parseInt(request.getParameter("corporationId"));
		corporatorDto.setCorporationId(corporationId);
		int corporatorId = Integer.parseInt(request.getParameter("corporatorId"));
		corporatorDto.setCorporatorId(corporatorId);
		corporatorDto.setUpdateOperatorId(operator.getOperatorId());
		corporatorDto.setPassword(getInitParameter("password"));
		
		//sessionにcorporationIdを保持
		session.setAttribute("corporationId", corporationId);
		
		//初期化処理
		try (Connection conn = DataSourceManager.getConnection()) {
			CorporatorDao corporatorDao = new CorporatorDao(conn);
			corporatorDao.resetPassword(corporatorDto);
			
			//更新した旨のメッセージ（パスワードを初期化しました）をセッションスコープに保持する
			session.setAttribute("message", "パスワードを初期化しました");
			
			//ListCorporatorServletにリダイレクト
			response.sendRedirect("list-corporator");
		
		} catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
			response.sendRedirect("operator-system-error.jsp");
			
		}
	}

}
