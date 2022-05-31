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

/**
 * Servlet implementation class ResetNewPasswordCorporator
 */
@WebServlet(name = "reset-new-password-corporator", urlPatterns = { "/reset-new-password-corporator" })
public class ResetNewPasswordCorporator extends HttpServlet {
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
		//�@�Z�b�V�����Ƀ��O�C����񂪓����ĂȂ��ꍇ�Alist-course.jsp�ɑJ�ڂ���
			HttpSession session = request.getSession(false);
			CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
								
					if (session == null || session.getAttribute("corporator") == null) {
						response.sendRedirect("login");
						return;
					}
								
					//�@�t�H�[���f�[�^�擾
					request.setCharacterEncoding("UTF-8");
					String newPassword = request.getParameter("newPassword");
					String newPassword2 = request.getParameter("newPassword2");
			
					
					// ���̓`�F�b�N,size�i�j�ŃG���[���b�Z�[�W�𔻕ʂ���
					List<String> errorMessageList = new ArrayList<>();
					errorMessageList = FieldValidator.passwordValidation(newPassword, newPassword2);
					if (errorMessageList.size() != 0) {
					session.setAttribute("message", errorMessageList);
					response.sendRedirect("change-corporator-password");
					return;
					}
					
					// Corporatordto�ɋl�߂�
					CorporatorDto CorporatorDto = new CorporatorDto();
					CorporatorDto.setPassword(newPassword);
					int corporatorId = corporator.getCorporatorId();
					CorporatorDto.setCorporatorId(corporatorId);
					CorporatorDto.setUpdateNumber(corporator.getUpdateNumber());
					
					// SQL�ڑ�
					try (Connection conn = DataSourceManager.getConnection()){
						CorporatorDao corporatorDao = new CorporatorDao(conn);
						
						//�p�X���[�h�X�V
						corporatorDao.updatePassword(CorporatorDto);
						//�X�V���b�Z�[�W�Z�b�g
						session.setAttribute("navbarMessage", "�p�X���[�h���X�V���܂���");
						
						//�������t���O�폜
						session.removeAttribute("isChangeRequired");
						
						// ListUserServlet�Ƀ��_�C���N�g����
						response.sendRedirect("list-user");

					} catch (SQLException | NamingException e) {
						//��O���b�Z�[�W�o�͕\��
						e.printStackTrace();
						//�V�X�e���G���[�y�[�W�ɑJ�ڂ���
						response.sendRedirect("corporator-system-error.jsp");
					}	
					
			}


}
