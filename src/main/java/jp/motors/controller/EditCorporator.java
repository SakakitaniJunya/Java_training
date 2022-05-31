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
import jp.motors.FieldValidator;
import jp.motors.dao.CorporatorDao;
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class EditCorporator
 */
@WebServlet(name = "edit-corporator", urlPatterns = { "/edit-corporator" })
public class EditCorporator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ���O�C���y�[�W���ɑJ�ڂ���
				request.getRequestDispatcher("login").forward(request, response);
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
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}	
		
		
		
		//�@�l�A�J�E���g���̎擾
				request.setCharacterEncoding("UTF-8");
				CorporatorDto dto = new CorporatorDto();
				int corporationId = Integer.parseInt(request.getParameter("corporationId"));
				dto.setCorporationId(corporationId);
				int corporatorId = Integer.parseInt(request.getParameter("corporatorId"));
				dto.setCorporatorId(corporatorId);
				String email = request.getParameter("email");
				dto.setEmail(email);
				dto.setFirstName(request.getParameter("firstName"));
				dto.setLastName(request.getParameter("lastName"));
				dto.setUpdateOperatorId(operator.getOperatorId());
			
				//session��corporationId��ێ�
				session.setAttribute("corporationId", corporationId);
				
				//���̓`�F�b�N
				List<String>  errorMessageList = new ArrayList<>();
				errorMessageList = FieldValidator.corporatorValidation(dto);
	
				//�G���[������ꍇlist-corporator�ɑJ��
				if (errorMessageList.size() != 0) {
					session.removeAttribute("errorMessageList");
					session.setAttribute("errorMessageList", errorMessageList);	
					response.sendRedirect("list-corporator");
					return;
				}
				
				//���[���A�h���X�ɏd�����Ȃ����m�F
				List<CorporatorDto> corporatorList = new ArrayList<>();
				try (Connection conn = DataSourceManager.getConnection()) {
					CorporatorDao corporatorDao = new CorporatorDao(conn);
					corporatorList = corporatorDao.selectNotMyId(corporatorId);
					
					//�����Ɠ������[���A�h���X��������l�݂̂��X�V����
					CorporatorDto corporatorDto = new CorporatorDto();
					corporatorDto = corporatorDao.selectMyId(corporatorId);
					if (email.equals(corporatorDto.getEmail())) {
						
						//Email�ȊO���A�b�v�f�[�g
						corporatorDao.updateNotEmail(dto);
						session.setAttribute("message", "�@�l�A�J�E���g���X�V���܂���");
						
						//list-corporation
						response.sendRedirect("list-corporator");
						return;
					}
					
					for(CorporatorDto corporatorLists: corporatorList) {
						
						if ( email.equals(corporatorLists.getEmail())) {
							session.setAttribute("errorMessageList", email + "�͂��łɑ��݂��܂�");
							response.sendRedirect("list-corporator");
							return;
						}
					}
				
					
					//��Џ��̓o�^
					corporatorDao.update(dto);
					//�X�V���b�Z�[�W
					session.setAttribute("message", "�@�l�A�J�E���g���X�V���܂���");
						
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
