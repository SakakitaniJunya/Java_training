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
		
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
		//user���擾
		//OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		//�Z�b�V�����ɏ�񂪓����Ă��Ȃ������烊�_�C���N�g	
		if (session == null || session.getAttribute("operator") == null) {
		//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
			response.sendRedirect("login");
			return;
		}
		//userId�擾
		int userId = Integer.parseInt(request.getParameter("userId"));
		request.setAttribute("userId", userId);
		
		// userName�擾
		request.setAttribute("userName", request.getParameter("userName"));
		//userId�ɕR�Â������b�Z�[�W
	try (Connection conn = DataSourceManager.getConnection()) {
		MessageDao messageDao = new MessageDao(conn);
		List<MessageDto> messageList = new ArrayList<>(); 
		messageList = messageDao.selectMessageByUserId(userId);
		
		//���b�Z�[�W��requestScope�ɃZ�b�g
		request.setAttribute("messageList", messageList);
		//user-message�ɑJ�ڂ���
		request.getRequestDispatcher("WEB-INF/message-user.jsp").forward(request, response);
		
	} catch (SQLException | NamingException e) {
		//�G���[����ʂɏo��
		e.printStackTrace();
		//�V�X�e���G���[��ʂɑJ��
	response.sendRedirect("operator-system-error.jsp");
		
	}
		
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�Z�b�V�����J�n
				HttpSession session = request.getSession(false);
					//user���擾
				OperatorDto operator = (OperatorDto) session.getAttribute("operator");
					//�Z�b�V�����ɏ�񂪓����Ă��Ȃ������烊�_�C���N�g	
				if (session == null || session.getAttribute("operator") == null) {
						//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
					response.sendRedirect("login");
					return;
					}

				//message�擾
				request.setCharacterEncoding("UTF-8");
				String message = request.getParameter("message");
				
				//���̓`�F�b�N
				if ("".equals(message)) {
					session.setAttribute("message", "�ԐM���e����͂��Ă�������");
					request.getRequestDispatcher("operator-message").forward(request, response);
					return;
				} 
				
				if(message.length() == 300) {
					session.setAttribute("message", "�ԐM���e��300�����ȓ��œ��͂��Ă�������");
					request.getRequestDispatcher("operator-message").forward(request, response);
					return;
				}
				
				//userId&operatorId�擾
				MessageDto messageDto = new MessageDto();
				messageDto.setMessage(message);
				int userId = Integer.parseInt(request.getParameter("userId"));
				int operatorId = operator.getOperatorId();
				
				messageDto.setUserId(userId);
				messageDto.setOperatorId(operatorId);
				
				//�f�[�^�x�[�X�ڑ�
				
				//userId�ɕR�Â������b�Z�[�W
				try (Connection conn = DataSourceManager.getConnection()) {
					MessageDao messageDao = new MessageDao(conn);
					messageDao.insertMessageByUserIdAndOperatorId(messageDto);
					
					session.removeAttribute("message");
					session.setAttribute("message", "�ԐM���܂���");
					
					//user-message�ɑJ�ڂ���
					response.sendRedirect("operator-message");
					//request.getRequestDispatcher("list-course").forward(request, response);
					
				} catch (SQLException | NamingException e) {
					//�G���[����ʂɏo��
					e.printStackTrace();
					//�V�X�e���G���[��ʂɑJ��
					response.sendRedirect("operator-system-error.jsp");
					
				}
	
	
	
	
	
	
	}

}
