package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.CorporatorDao;
import jp.motors.dto.CorporatorDto;

/**
 * Servlet implementation class DeleteCorporator
 */
@WebServlet(name = "delete-corporator", urlPatterns = { "/delete-corporator" })
public class DeleteCorporator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ���O�C���y�[�W���ɑJ�ڂ���
		response.sendRedirect("login");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
								
		//�Z�b�V��������
		// OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
			// �g�b�v�y�[�W�Ɉړ�
			response.sendRedirect("login");
			return;
			}	
				
		//�@�l�A�J�E���g���̎擾
		request.setCharacterEncoding("UTF-8");
		CorporatorDto dto = new CorporatorDto();
		int corporationId = Integer.parseInt(request.getParameter("corporationId"));
		dto.setCorporationId(corporationId);
		int corporatorId = Integer.parseInt(request.getParameter("corporatorId"));
		dto.setCorporatorId(corporatorId);
		
		//session��corporationId��ێ�
		session.setAttribute("corporationId", corporationId);
		
		
		
		try (Connection conn = DataSourceManager.getConnection()) {
			CorporatorDao corporatorDao = new CorporatorDao(conn);
			
			//��Џ��̍폜
			corporatorDao.deleteById(corporatorId);
			//�X�V���b�Z�[�W
			session.setAttribute("message", "�@�l�A�J�E���g���폜���܂���");
				
			//list-corporation
			response.sendRedirect("list-corporator");
					
				
		} catch (SQLException | NamingException e) {
				//�G���[���o�͕\��
				e.printStackTrace();
				//�V�X�e���G���[��ʂɑJ�ڂ���
				response.sendRedirect("operator-system-error.jsp");
				
		}
		
		
	}

}
