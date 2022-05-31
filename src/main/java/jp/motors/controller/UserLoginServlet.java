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
import jp.motors.dao.UserDao;
import jp.motors.dto.UserDto;

//　初期パスワードをinitParamsに格納する
@WebServlet(urlPatterns={"/user-login"}, initParams={@WebInitParam(name="password", value="codetrain123")})
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// トップページ(コース一覧)に遷移する
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// フォームのデータを取得する
		String loginId = request.getParameter("id");
		String loginPassword = request.getParameter("password");
		String uri = request.getParameter("uri");

		// セッションを取得する
		HttpSession session = request.getSession(true);

		// ログインID、パスワードが未入力の場合
		if ("".equals(loginId) || "".equals(loginPassword)) {

			session.setAttribute("navbarMessage", "メールアドレス、パスワードを入力してください");
			
			// ログイン処理前のページ情報に遷移する
			response.sendRedirect(uri);
			return;
		}
		
		try (Connection conn = DataSourceManager.getConnection()) {

			// ログイン処理
			UserDao loginDao = new UserDao(conn);
			UserDto userDto = loginDao.findByIdAndPassword(loginId, loginPassword);
			
			session.removeAttribute("navbarMessage");

			// ログイン失敗時
			if (userDto == null) {
				session.setAttribute("navbarMessage", "メールアドレスまたはパスワードが間違っています");
			}
			
			
			// 初期化時刻が30分いないか
			boolean resetFlg = loginDao.resetCheck(loginId, loginPassword);
			
			//　初期化時刻を過ぎている場合
			if (getInitParameter("password").equals(loginPassword) && !resetFlg) {
				session.setAttribute("navbarMessage", "初期化期限が過ぎています");
				response.sendRedirect(uri);
				return;
			}
			
			// 初回ログイン時&初期化時刻の３０分以内
			if (getInitParameter("password").equals(loginPassword) && resetFlg) {
				session.setAttribute("user", userDto);
				session.setAttribute("isChangeRequired", true);
				response.sendRedirect("change-user-password");
				return;
			}
			
			session.setAttribute("user", userDto);
			// ログイン処理前のページ情報に遷移する
			response.sendRedirect(uri);
			
		} catch (SQLException | NamingException e) {
			// 例外メッセージを出力表示
			e.printStackTrace();
			
			// システムエラーに遷移する
			response.sendRedirect("user-system-error.jsp");
		}
	}
}
