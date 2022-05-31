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
import jp.motors.dao.CorporatorDao;
import jp.motors.dto.CorporatorDto;

/**
 * Servlet implementation class ListCorporatorServlet
 */
@WebServlet(name = "list-corporator", urlPatterns = { "/list-corporator" })
public class ListCorporatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
				
		//�Z�b�V��������
		//OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
			// �g�b�v�y�[�W�Ɉړ�
			response.sendRedirect("login");
			return;
		}
		
			
		//SQL����l�擾
		try (Connection conn = DataSourceManager.getConnection()) {
				
			int corporationId = 0;
			//�Z�b�V��������
			if( session.getAttribute("corporationId") != null ) {
				corporationId = (int) session.getAttribute("corporationId");
			}
			if (corporationId == 0) {	
			// corporationId���擾
				corporationId = Integer.parseInt(request.getParameter("corporationId"));
			
			}
				
				
				request.setAttribute("corporationId", corporationId);
				// corporationId���Z�b�g
			
				// �@�l�J�E���g���S���擾(ID)
				List<CorporatorDto> corporatorList =  new ArrayList<>(); 
				CorporatorDao corporatorDao = new CorporatorDao(conn);
				corporatorList = corporatorDao.selectByCorporationId(corporationId);
				
				
				//����������	
				//List<CorporatorDto> corporatorList2 =  corporatorDao.selectAll();
				List<Boolean> resetPasswordList = corporatorDao.findResetPasswordById(corporatorList);
				request.setAttribute("resetPasswordList", resetPasswordList);
				
				int count = 0;
				for (CorporatorDto corporatordtos: corporatorList) {
					corporatordtos.setResetFlg(resetPasswordList.get(count));
					count++;
				}
				
				request.setAttribute("corporatorList", corporatorList);
				
				//���N�G�X�g�X�R�[�v�ɃZ�b�g
				request.setAttribute("corporatorList", corporatorList);
		
			
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
				request.getRequestDispatcher("WEB-INF/list-corporator.jsp").forward(request, response);
		
				
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
