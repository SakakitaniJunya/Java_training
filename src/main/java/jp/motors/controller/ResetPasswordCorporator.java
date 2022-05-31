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
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class ResetPasswordCorporator
 */
@WebServlet(urlPatterns={"/reset-password-corporator"},initParams={@WebInitParam(name="password", value="codetrain123")})
public class ResetPasswordCorporator extends HttpServlet {
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
				
		//�Z�b�V��������
		OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
			// �g�b�v�y�[�W�Ɉړ�
			response.sendRedirect("login");
			return;
		}
		
		//�l�擾
		request.setCharacterEncoding("UTF-8");
		CorporatorDto corporatorDto = new CorporatorDto();
		int corporationId = Integer.parseInt(request.getParameter("corporationId"));
		corporatorDto.setCorporationId(corporationId);
		int corporatorId = Integer.parseInt(request.getParameter("corporatorId"));
		corporatorDto.setCorporatorId(corporatorId);
		corporatorDto.setUpdateOperatorId(operator.getOperatorId());
		corporatorDto.setPassword(getInitParameter("password"));
		
		//session��corporationId��ێ�
		session.setAttribute("corporationId", corporationId);
		
		//����������
		try (Connection conn = DataSourceManager.getConnection()) {
			CorporatorDao corporatorDao = new CorporatorDao(conn);
			corporatorDao.resetPassword(corporatorDto);
			
			//�X�V�����|�̃��b�Z�[�W�i�p�X���[�h�����������܂����j���Z�b�V�����X�R�[�v�ɕێ�����
			session.setAttribute("message", "�p�X���[�h�����������܂���");
			
			//ListCorporatorServlet�Ƀ��_�C���N�g
			response.sendRedirect("list-corporator");
		
		} catch (SQLException | NamingException e) {
			//�G���[����ʂɏo��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ��
			response.sendRedirect("operator-system-error.jsp");
			
		}
	}

}