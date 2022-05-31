package jp.motors.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.UserDto;
import jp.motors.dao.UserDao;

/**
 * Servlet implementation class ResetPasswordUserServlet
 */
@WebServlet(urlPatterns={"/reset-password-user"},initParams={@WebInitParam(name="password", value="codetrain123")})
public class ResetPasswordUserServlet extends HttpServlet {
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
		if (session == null || session.getAttribute("corporator") == null) {
			response.sendRedirect("login");
			return;
		}
		
		//ユーザ情報取得
		CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
		UserDto userDto = new UserDto();
		request.setCharacterEncoding("UTF-8");
		userDto.setUserId(Integer.parseInt(request.getParameter("userId")));
		userDto.setUpdateCorporatorId(corporator.getCorporatorId());
		
		//パスワード初期化
		
		// 初期化パスワードをセット
		userDto.setPassword(getInitParameter("password"));
		
		//更新メッセージセット
		try (Connection conn = DataSourceManager.getConnection()) {
			UserDao userDao = new UserDao(conn);
			userDao.resetPassword(userDto);
		
			//更新した旨のメッセージ（パスワードを初期化しました）をセッションスコープに保持する
			session.setAttribute("message", "パスワードを初期化しました");
			
			//ListUserServletにリダイレクト
			response.sendRedirect("list-user");
			
		} catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
			response.sendRedirect("corporator-system-error.jsp");
			
		}
	}

}
