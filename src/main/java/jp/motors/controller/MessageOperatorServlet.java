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
		
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
		
		//�Z�b�V�����̒��g���Ȃ�������Alogin.jsp�ɑJ��
		//OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
			// �g�b�v�y�[�W�Ɉړ�
			response.sendRedirect("login");
			return;
		}
		
		//�Z�b�V������message�����N�G�X�g�ɕێ�����A�Z�b�V��������폜����
		request.setAttribute("message", session.getAttribute("message"));
		session.removeAttribute("message");
					
		// �i�u�o�[���b�Z�[�W�����N�G�X�g�ɕێ�����
		request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
		session.removeAttribute("navbarMessage");
		
		//�G���[���b�Z�[�W���N�G�X�g�ɕێ�����A�Z�b�V��������폜����
		request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
		session.removeAttribute("errorMessageList");
		
		try (Connection conn = DataSourceManager.getConnection()) {
			List<UserDto> userList =  new ArrayList<>(); 
			MessageDao messageDao = new MessageDao(conn);
			UserDao userDao = new UserDao(conn);
			userList = userDao.selectAll();
			
		//���ԐM���b�Z�[�W���Ƃ��Ă���newMessageList
			List<OperatorDto> newMessageList = new ArrayList<>(); 
			newMessageList = messageDao.selectMessageByNotSend(userList);
		
		//�ԐM�ς݃��b�Z�[�W���Ƃ��Ă��� MessageList
			List<OperatorDto> messageList = new ArrayList<>(); 
			messageList = messageDao.selectMessageBySend(userList);
			
		//���N�G�X�g�X�R�[�v�ɃZ�b�g
			request.setAttribute("newMessageList", newMessageList);
			request.setAttribute("messageList", messageList);
		//���b�Z�[�W���X�g(message-list-operator)�ɑJ�ڂ���
			request.getRequestDispatcher("WEB-INF/message-list-operator.jsp").forward(request, response);
			
		}  catch (SQLException | NamingException e) {
			//�G���[����ʂɏo��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ��
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
