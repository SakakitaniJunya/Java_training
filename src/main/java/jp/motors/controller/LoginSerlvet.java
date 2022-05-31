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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.OperatorDto;


//初期パスワードをinitParamsに格納する
@WebServlet(urlPatterns={"/login"}, initParams={@WebInitParam(name="password", value="codetrain123")})
public class LoginSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// セッションを取得する
		HttpSession session = request.getSession(true);
		session.removeAttribute("operator");
		session.removeAttribute("corporator");
		
		// メッセージをリクエストに保持する、セッションから削除する
		request.setAttribute("message", session.getAttribute("message"));
		session.removeAttribute("message");
		
		// トップページに遷移する
		request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// フォームのデータを取得する
		String loginId = request.getParameter("id");
		String loginPassword = request.getParameter("password");
		
		// セッションを取得する
		HttpSession session = request.getSession(true);
		
		// ログインID、パスワードが未入力の場合
		if ("".equals(loginId) || "".equals(loginPassword)) {

			session.setAttribute("message", "メールアドレス、パスワードを入力してください");
					
			response.sendRedirect("login");
			return;
		}
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			// ログイン処理 法人の場合
			CorporatorDao loginDao = new CorporatorDao(conn);
			CorporatorDto corporatorDto = loginDao.findByIdAndPassword(loginId, loginPassword);
			
			// 法人でない場合
			if (corporatorDto == null) {

				// ログイン処理 運用管理の場合
				OperatorDao loginoDao = new OperatorDao(conn);
				OperatorDto operatorDto = loginoDao.findByIdAndPassword(loginId, loginPassword);

				// 初期化時刻が30分いないか
				boolean resetOperotorFlg = loginoDao.resetCheck(loginId, loginPassword);
				
				//　初期化時刻を過ぎている場合
				if (getInitParameter("password").equals(loginPassword) && !resetOperotorFlg) {
					session.setAttribute("message", "初期化期限が過ぎています");
					response.sendRedirect("login");
					return;
					}
				
				if (operatorDto == null) {
					session.setAttribute("message", "メールアドレスまたはパスワードが間違っています");
					response.sendRedirect("login");
					return;
				}
				
				// 初回ログイン時&初期化時刻の３０分以内
				if (getInitParameter("password").equals(loginPassword) && resetOperotorFlg) {
						session.setAttribute("operator", operatorDto);
						session.setAttribute("isChangeRequired", true);
						response.sendRedirect("change-operator-password");
						return;
				}
				
				session.setAttribute("operator", operatorDto);
				// コース一覧になっているがお問い合わせに変更
				response.sendRedirect("operator-message");
				return;
			} 
			

			//初期化時刻が30分いないか
			boolean resetCorporatorFlg = loginDao.resetCheck(loginId, loginPassword);
			
			//　初期化時刻を過ぎている場合
			if (getInitParameter("password").equals(loginPassword) && !resetCorporatorFlg) {
				session.setAttribute("message", "初期化期限が過ぎています");
				request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
				return;
			}
			
			//初回ログイン時&初期化時刻の３０分以内
			if (getInitParameter("password").equals(loginPassword) && resetCorporatorFlg) {
					session.setAttribute("corporator", corporatorDto);
					session.setAttribute("isChangeRequired", true);
					response.sendRedirect("change-corporator-password");
					return;
			}
			
			session.setAttribute("corporator", corporatorDto);
			// ログイン処理前のページ情報に遷移する
			response.sendRedirect("list-user");
				
		} catch (SQLException | NamingException e) {
			// 例外メッセージを出力表示
			e.printStackTrace();
				
			//システムエラーに遷移する
			response.sendRedirect("corporator-system-error.jsp");
		}
	} 
}

