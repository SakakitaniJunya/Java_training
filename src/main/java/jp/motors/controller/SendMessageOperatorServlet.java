package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.MessageDao;
import jp.motors.dto.MessageDto;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class SendMessageOperatorServlet
 */
@WebServlet(name = "send-message-operator", urlPatterns = { "/send-message-operator" })
public class SendMessageOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//セッション開始
		HttpSession session = request.getSession(false);
		//user情報取得
		//OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		//セッションに情報が入っていなかったらリダイレクト	
		if (session == null || session.getAttribute("operator") == null) {
		//コース一覧ページに遷移する
			response.sendRedirect("login");
			return;
		}
		//userId取得
		int userId = Integer.parseInt(request.getParameter("userId"));
		request.setAttribute("userId", userId);
		
		// userName取得
		request.setAttribute("userName", request.getParameter("userName"));
		//userIdに紐づいたメッセージ
	try (Connection conn = DataSourceManager.getConnection()) {
		MessageDao messageDao = new MessageDao(conn);
		List<MessageDto> messageList = new ArrayList<>(); 
		messageList = messageDao.selectMessageByUserId(userId);
		
		//メッセージをrequestScopeにセット
		request.setAttribute("messageList", messageList);
		//user-messageに遷移する
		request.getRequestDispatcher("WEB-INF/message-user.jsp").forward(request, response);
		
	} catch (SQLException | NamingException e) {
		//エラーを画面に出力
		e.printStackTrace();
		//システムエラー画面に遷移
	response.sendRedirect("operator-system-error.jsp");
		
	}
		
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//セッション開始
				HttpSession session = request.getSession(false);
					//user情報取得
				OperatorDto operator = (OperatorDto) session.getAttribute("operator");
					//セッションに情報が入っていなかったらリダイレクト	
				if (session == null || session.getAttribute("operator") == null) {
						//コース一覧ページに遷移する
					response.sendRedirect("login");
					return;
					}

				//message取得
				request.setCharacterEncoding("UTF-8");
				String message = request.getParameter("message");
				
				//入力チェック
				if ("".equals(message)) {
					session.setAttribute("message", "返信内容を入力してください");
					request.getRequestDispatcher("operator-message").forward(request, response);
					return;
				} 
				
				if(message.length() == 300) {
					session.setAttribute("message", "返信内容は300文字以内で入力してください");
					request.getRequestDispatcher("operator-message").forward(request, response);
					return;
				}
				
				//userId&operatorId取得
				MessageDto messageDto = new MessageDto();
				messageDto.setMessage(message);
				int userId = Integer.parseInt(request.getParameter("userId"));
				int operatorId = operator.getOperatorId();
				
				messageDto.setUserId(userId);
				messageDto.setOperatorId(operatorId);
				
				//データベース接続
				
				//userIdに紐づいたメッセージ
				try (Connection conn = DataSourceManager.getConnection()) {
					MessageDao messageDao = new MessageDao(conn);
					messageDao.insertMessageByUserIdAndOperatorId(messageDto);
					
					session.removeAttribute("message");
					session.setAttribute("message", "返信しました");
					
					//user-messageに遷移する
					response.sendRedirect("operator-message");
					//request.getRequestDispatcher("list-course").forward(request, response);
					
				} catch (SQLException | NamingException e) {
					//エラーを画面に出力
					e.printStackTrace();
					//システムエラー画面に遷移
					response.sendRedirect("operator-system-error.jsp");
					
				}
	
	
	
	
	
	
	}

}
