package jp.motors.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
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
 * Servlet implementation class ResetPasswordUserServlet
 */
@WebServlet(urlPatterns={"/reset-password-user"},initParams={@WebInitParam(name="password", value="codetrain123")})
public class ResetPasswordUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("corporator") == null) {
			response.sendRedirect("login");
			return;
		}
		
		//���[�U���擾
		CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
		UserDto userDto = new UserDto();
		request.setCharacterEncoding("UTF-8");
		userDto.setUserId(Integer.parseInt(request.getParameter("userId")));
		userDto.setUpdateCorporatorId(corporator.getCorporatorId());
		
		//�p�X���[�h������
		
		// �������p�X���[�h���Z�b�g
		userDto.setPassword(getInitParameter("password"));
		
		//�X�V���b�Z�[�W�Z�b�g
		try (Connection conn = DataSourceManager.getConnection()) {
			UserDao userDao = new UserDao(conn);
			userDao.resetPassword(userDto);
		
			//�X�V�����|�̃��b�Z�[�W�i�p�X���[�h�����������܂����j���Z�b�V�����X�R�[�v�ɕێ�����
			session.setAttribute("message", "�p�X���[�h�����������܂���");
			
			//ListUserServlet�Ƀ��_�C���N�g
			response.sendRedirect("list-user");
			
		} catch (SQLException | NamingException e) {
			//�G���[����ʂɏo��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ��
			response.sendRedirect("corporator-system-error.jsp");
			
		}
	}

}
