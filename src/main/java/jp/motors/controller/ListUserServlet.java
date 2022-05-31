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
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
		
		
		//�Z�b�V�������Ȃ��ꍇ�Alogin�ɑJ��
		if (session == null || session.getAttribute("corporator") == null) {
			//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
		response.sendRedirect("login");
		return;
		}
		
		//���[�U���S���擾
		try (Connection conn = DataSourceManager.getConnection()) {
			List<UserDto> userList =  new ArrayList<>(); 
			UserDao userDao = new UserDao(conn);
			
			//�Z�b�V���������Џ����擾
			CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
			userList = userDao.selectByUserId(corporator.getCorporationId());
			
			
			// ���񃍃O�C����
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
			//userDto�ɏ�����Flg���Z�b�g
				int count = 0;
			for (UserDto userdtos: userList) {
				userdtos.setResetFlg(resetPasswordList.get(count));
				count++;
			}
			
			//���[�U�������N�G�X�g�X�R�[�v�ɃZ�b�g
			request.setAttribute("userList", userList);
			
			//���ݎ����擾
			 Date dateObj = new Date();
			 //SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
			 // ���������w��t�H�[�}�b�g�̕�����Ŏ擾
			 //String display = format.format( dateObj );
			 request.setAttribute("dateObj", dateObj); 
			 
			 
			 
			 
			 
			 
			
			//�Z�b�V������message�����N�G�X�g�ɕێ�����A�Z�b�V��������폜����
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
						
			// �i�u�o�[���b�Z�[�W�����N�G�X�g�ɕێ�����
			request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			//�G���[���b�Z�[�W���N�G�X�g�ɕێ�����A�Z�b�V��������폜����
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessageList");
			
		
		//list-user�ɑJ�ڂ���
		request.getRequestDispatcher("WEB-INF/list-user.jsp").forward(request, response);
		
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
		doGet(request, response);
	}

}
