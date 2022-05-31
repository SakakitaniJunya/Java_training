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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class ResetNewPasswordOperator
 */
@WebServlet(name = "reset-new-password-operator", urlPatterns = { "/reset-new-password-operator" })
public class ResetNewPasswordOperator extends HttpServlet {
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
					OperatorDto operator = (OperatorDto) session.getAttribute("operator");
										
							if (session == null || session.getAttribute("operator") == null) {
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
							session.setAttribute("navbarMessage", errorMessageList);
							response.sendRedirect("change-operator-password");
							return;
							}
							
							// Operatordto�ɋl�߂�
							OperatorDto OperatorDto = new OperatorDto();
							OperatorDto.setPassword(newPassword);
							int operatorId = operator.getOperatorId();
							OperatorDto.setOperatorId(operatorId);
							OperatorDto.setUpdateNumber(operator.getUpdateNumber());
							
							// SQL�ڑ�
							try (Connection conn = DataSourceManager.getConnection()){
								OperatorDao operatorDao = new OperatorDao(conn);
								
								//�p�X���[�h�X�V
								operatorDao.updatePassword(OperatorDto);
								//�X�V���b�Z�[�W�Z�b�g
								session.setAttribute("navbarMessage", "�p�X���[�h���X�V���܂���");
								
								//�������t���O�폜
								session.removeAttribute("isChangeRequired");
								
								// Operator-Message�Ƀ��_�C���N�g����
								response.sendRedirect("operator-message");

							} catch (SQLException | NamingException e) {
								//��O���b�Z�[�W�o�͕\��
								e.printStackTrace();
								//�V�X�e���G���[�y�[�W�ɑJ�ڂ���
								response.sendRedirect("operator-system-error.jsp");
							}	
							
					}

}
