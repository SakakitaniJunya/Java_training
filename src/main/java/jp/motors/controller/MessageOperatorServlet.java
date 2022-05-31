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
import jp.motors.dao.UserDao;
import jp.motors.dto.OperatorDto;
import jp.motors.dto.UserDto;

/**
 * Servlet implementation class MessageOperatorServlet
 */
@WebServlet("/operator-message")
public class MessageOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//セッション開始
		HttpSession session = request.getSession(false);
		
		//セッションの中身がなかったら、login.jspに遷移
		//OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
			// トップページに移動
			response.sendRedirect("login");
			return;
		}
		
		//セッションのmessageをリクエストに保持する、セッションから削除する
		request.setAttribute("message", session.getAttribute("message"));
		session.removeAttribute("message");
					
		// ナブバーメッセージをリクエストに保持する
		request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
		session.removeAttribute("navbarMessage");
		
		//エラーメッセージをクエストに保持する、セッションから削除する
		request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
		session.removeAttribute("errorMessageList");
		
		try (Connection conn = DataSourceManager.getConnection()) {
			List<UserDto> userList =  new ArrayList<>(); 
			MessageDao messageDao = new MessageDao(conn);
			UserDao userDao = new UserDao(conn);
			userList = userDao.selectAll();
			
		//未返信メッセージをとってくるnewMessageList
			List<OperatorDto> newMessageList = new ArrayList<>(); 
			newMessageList = messageDao.selectMessageByNotSend(userList);
		
		//返信済みメッセージをとってくる MessageList
			List<OperatorDto> messageList = new ArrayList<>(); 
			messageList = messageDao.selectMessageBySend(userList);
			
		//リクエストスコープにセット
			request.setAttribute("newMessageList", newMessageList);
			request.setAttribute("messageList", messageList);
		//メッセージリスト(message-list-operator)に遷移する
			request.getRequestDispatcher("WEB-INF/message-list-operator.jsp").forward(request, response);
			
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
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		
	}

}
