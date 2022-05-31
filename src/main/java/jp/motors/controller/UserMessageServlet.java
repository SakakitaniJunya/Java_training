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
	
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
	
		//�Z�b�V�����ɏ�񂪓����Ă��Ȃ������烊�_�C���N�g	
		if (session == null || session.getAttribute("user") == null) {
		//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
	
		//user���擾
		UserDto user = (UserDto) session.getAttribute("user");
		
		//userId�擾
		int userId = user.getUserId();
		
		//userId�ɕR�Â������b�Z�[�W
		try (Connection conn = DataSourceManager.getConnection()) {
			MessageDao messageDao = new MessageDao(conn);
			List<MessageDto> messageList = new ArrayList<>(); 
			messageList = messageDao.selectMessageByUserId(userId);
			
			//���b�Z�[�W��requestScope�ɃZ�b�g
			request.setAttribute("messageList", messageList);
			
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			//user-message�ɑJ�ڂ���
			request.getRequestDispatcher("WEB-INF/message-user.jsp").forward(request, response);
			
		} catch (SQLException | NamingException e) {
			//�G���[����ʂɏo��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ��
		response.sendRedirect("user-system-error.jsp");
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
			//user���擾
		UserDto user = (UserDto) session.getAttribute("user");
			//�Z�b�V�����ɏ�񂪓����Ă��Ȃ������烊�_�C���N�g	
		if (session == null || session.getAttribute("user") == null) {
				//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
			}

		//message�擾
		request.setCharacterEncoding("UTF-8");
		String message = request.getParameter("message");
		
		//���̓`�F�b�N
		if ("".equals(message)) {
			session.setAttribute("message", "���₢���킹���e����͂��Ă�������");
			response.sendRedirect("user-message");
			return;
		} 
		
		if(message.length() == 300) {
			session.setAttribute("message", "���₢���킹���e��300�����ȓ��œ��͂��Ă�������");
			response.sendRedirect("user-message");
			return;
		}
		
		//userId�擾
		MessageDto messageDto = new MessageDto();
		messageDto.setMessage(message);
		messageDto.setUserId(user.getUserId());
		
		
		//�f�[�^�x�[�X�ڑ�
		
		//userId�ɕR�Â������b�Z�[�W
		try (Connection conn = DataSourceManager.getConnection()) {
			MessageDao messageDao = new MessageDao(conn);
			messageDao.insertMessageByUserId(messageDto);
			
			session.setAttribute("message", "���M���܂���");
			
			//user-message�ɑJ�ڂ���
			//response.sendRedirect("list-course");
			response.sendRedirect("user-message");
			
		} catch (SQLException | NamingException e) {
			//�G���[����ʂɏo��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ��
			response.sendRedirect("user-system-error.jsp");
			
		}
		
		
	}
}

