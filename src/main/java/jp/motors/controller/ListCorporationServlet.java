package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.CorporationDao;
import jp.motors.dto.CorporationDto;

/**
 * Servlet implementation class ListCorporationServlet
 */
@WebServlet(name = "list-corporation", urlPatterns = { "/list-corporation" })
public class ListCorporationServlet extends HttpServlet {
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
			
			//�@�@�lID�������Ă�����Z�b�V��������폜
			if( session.getAttribute("corporationId") != null ) {
				session.removeAttribute("corporationId");
			}
			
			//���ݎ����擾
			 Date dateObj = new Date();
			 //SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
			 // ���������w��t�H�[�}�b�g�̕�����Ŏ擾
			 //String display = format.format( dateObj );
			 request.setAttribute("dateObj", dateObj); 
			 
			 
			
			//SQL����l�擾
		try (Connection conn = DataSourceManager.getConnection()) {
				
				//�^�p�ҏ��S���擾
				List<CorporationDto> corporationList =  new ArrayList<>(); 
				CorporationDao corporationDao = new CorporationDao(conn);
				corporationList = corporationDao.selectAll();
			
			    //���N�G�X�g�X�R�[�v�ɃZ�b�g
				request.setAttribute("corporationList", corporationList);
				
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
				request.getRequestDispatcher("WEB-INF/list-corporation.jsp").forward(request, response);
		
				
			//list-corporator.jsp�ɑJ��
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
