package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.MessageDao;
import jp.motors.dto.UserDto;
import jp.motors.dto.MessageDto;

/**
 * Servlet implementation class UserMessageServlet
 */
@WebServlet(name = "user-message", urlPatterns = { "/user-message" })
public class UserMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//セッション開始
		HttpSession session = request.getSession(false);
	
		//セッションに情報が入っていなかったらリダイレクト	
		if (session == null || session.getAttribute("user") == null) {
		//コース一覧ページに遷移する
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
	
		//user情報取得
		UserDto user = (UserDto) session.getAttribute("user");
		
		//userId取得
		int userId = user.getUserId();
		
		//userIdに紐づいたメッセージ
		try (Connection conn = DataSourceManager.getConnection()) {
			MessageDao messageDao = new MessageDao(conn);
			List<MessageDto> messageList = new ArrayList<>(); 
			messageList = messageDao.selectMessageByUserId(userId);
			
			//メッセージをrequestScopeにセット
			request.setAttribute("messageList", messageList);
			
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			//user-messageに遷移する
			request.getRequestDispatcher("WEB-INF/message-user.jsp").forward(request, response);
			
		} catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
		response.sendRedirect("user-system-error.jsp");
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//セッション開始
		HttpSession session = request.getSession(false);
			//user情報取得
		UserDto user = (UserDto) session.getAttribute("user");
			//セッションに情報が入っていなかったらリダイレクト	
		if (session == null || session.getAttribute("user") == null) {
				//コース一覧ページに遷移する
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
			}

		//message取得
		request.setCharacterEncoding("UTF-8");
		String message = request.getParameter("message");
		
		//入力チェック
		if ("".equals(message)) {
			session.setAttribute("message", "お問い合わせ内容を入力してください");
			response.sendRedirect("user-message");
			return;
		} 
		
		if(message.length() == 300) {
			session.setAttribute("message", "お問い合わせ内容は300文字以内で入力してください");
			response.sendRedirect("user-message");
			return;
		}
		
		//userId取得
		MessageDto messageDto = new MessageDto();
		messageDto.setMessage(message);
		messageDto.setUserId(user.getUserId());
		
		
		//データベース接続
		
		//userIdに紐づいたメッセージ
		try (Connection conn = DataSourceManager.getConnection()) {
			MessageDao messageDao = new MessageDao(conn);
			messageDao.insertMessageByUserId(messageDto);
			
			session.setAttribute("message", "送信しました");
			
			//user-messageに遷移する
			//response.sendRedirect("list-course");
			response.sendRedirect("user-message");
			
		} catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
			response.sendRedirect("user-system-error.jsp");
			
		}
		
		
	}
}

