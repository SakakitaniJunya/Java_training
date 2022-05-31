package jp.motors.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Connection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
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
 * Servlet implementation class ListUserServlet
 */
@WebServlet("/list-user")
public class ListUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//セッション開始
		HttpSession session = request.getSession(false);
		
		
		//セッションがない場合、loginに遷移
		if (session == null || session.getAttribute("corporator") == null) {
			//コース一覧ページに遷移する
		response.sendRedirect("login");
		return;
		}
		
		//ユーザ情報全件取得
		try (Connection conn = DataSourceManager.getConnection()) {
			List<UserDto> userList =  new ArrayList<>(); 
			UserDao userDao = new UserDao(conn);
			
			//セッションから会社情報を取得
			CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
			userList = userDao.selectByUserId(corporator.getCorporationId());
			
			
			// 初回ログイン時
			//for (UserDto user: userList)
			//if (getInitParameter("password").equals(user.loginPassword)) {
			//	session.setAttribute("isChangeRequired", true);
			//	response.sendRedirect("change--password");
			//	return;
			//}
			
			//List<UserDto> userDto2 = userDao.selectAll();
			List<Boolean> resetPasswordList = userDao.findResetPasswordById(userList );
			request.setAttribute("resetPasswordList", resetPasswordList);
			
			
			//for (int i = 0; i < userList.size();i++) {
			//	userList.add(i, userList.setResetFlg(resetPasswordList.get(i));
			//}
			//userDtoに初期化Flgをセット
				int count = 0;
			for (UserDto userdtos: userList) {
				userdtos.setResetFlg(resetPasswordList.get(count));
				count++;
			}
			
			//ユーザ情報をリクエストスコープにセット
			request.setAttribute("userList", userList);
			
			//現在時刻取得
			 Date dateObj = new Date();
			 //SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
			 // 日時情報を指定フォーマットの文字列で取得
			 //String display = format.format( dateObj );
			 request.setAttribute("dateObj", dateObj); 
			 
			 
			 
			 
			 
			 
			
			//セッションのmessageをリクエストに保持する、セッションから削除する
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
						
			// ナブバーメッセージをリクエストに保持する
			request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			//エラーメッセージをクエストに保持する、セッションから削除する
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessageList");
			
		
		//list-userに遷移する
		request.getRequestDispatcher("WEB-INF/list-user.jsp").forward(request, response);
		
		}  catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
		response.sendRedirect("corporator-system-error.jsp");
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
