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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.OperatorDto;


//�����p�X���[�h��initParams�Ɋi�[����
@WebServlet(urlPatterns={"/login"}, initParams={@WebInitParam(name="password", value="codetrain123")})
public class LoginSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// �Z�b�V�������擾����
		HttpSession session = request.getSession(true);
		session.removeAttribute("operator");
		session.removeAttribute("corporator");
		
		// ���b�Z�[�W�����N�G�X�g�ɕێ�����A�Z�b�V��������폜����
		request.setAttribute("message", session.getAttribute("message"));
		session.removeAttribute("message");
		
		// �g�b�v�y�[�W�ɑJ�ڂ���
		request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �t�H�[���̃f�[�^���擾����
		String loginId = request.getParameter("id");
		String loginPassword = request.getParameter("password");
		
		// �Z�b�V�������擾����
		HttpSession session = request.getSession(true);
		
		// ���O�C��ID�A�p�X���[�h�������͂̏ꍇ
		if ("".equals(loginId) || "".equals(loginPassword)) {

			session.setAttribute("message", "���[���A�h���X�A�p�X���[�h����͂��Ă�������");
					
			response.sendRedirect("login");
			return;
		}
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			// ���O�C������ �@�l�̏ꍇ
			CorporatorDao loginDao = new CorporatorDao(conn);
			CorporatorDto corporatorDto = loginDao.findByIdAndPassword(loginId, loginPassword);
			
			// �@�l�łȂ��ꍇ
			if (corporatorDto == null) {

				// ���O�C������ �^�p�Ǘ��̏ꍇ
				OperatorDao loginoDao = new OperatorDao(conn);
				OperatorDto operatorDto = loginoDao.findByIdAndPassword(loginId, loginPassword);

				// ������������30�����Ȃ���
				boolean resetOperotorFlg = loginoDao.resetCheck(loginId, loginPassword);
				
				//�@�������������߂��Ă���ꍇ
				if (getInitParameter("password").equals(loginPassword) && !resetOperotorFlg) {
					session.setAttribute("message", "�������������߂��Ă��܂�");
					response.sendRedirect("login");
					return;
					}
				
				if (operatorDto == null) {
					session.setAttribute("message", "���[���A�h���X�܂��̓p�X���[�h���Ԉ���Ă��܂�");
					response.sendRedirect("login");
					return;
				}
				
				// ���񃍃O�C����&�����������̂R�O���ȓ�
				if (getInitParameter("password").equals(loginPassword) && resetOperotorFlg) {
						session.setAttribute("operator", operatorDto);
						session.setAttribute("isChangeRequired", true);
						response.sendRedirect("change-operator-password");
						return;
				}
				
				session.setAttribute("operator", operatorDto);
				// �R�[�X�ꗗ�ɂȂ��Ă��邪���₢���킹�ɕύX
				response.sendRedirect("operator-message");
				return;
			} 
			

			//������������30�����Ȃ���
			boolean resetCorporatorFlg = loginDao.resetCheck(loginId, loginPassword);
			
			//�@�������������߂��Ă���ꍇ
			if (getInitParameter("password").equals(loginPassword) && !resetCorporatorFlg) {
				session.setAttribute("message", "�������������߂��Ă��܂�");
				request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
				return;
			}
			
			//���񃍃O�C����&�����������̂R�O���ȓ�
			if (getInitParameter("password").equals(loginPassword) && resetCorporatorFlg) {
					session.setAttribute("corporator", corporatorDto);
					session.setAttribute("isChangeRequired", true);
					response.sendRedirect("change-corporator-password");
					return;
			}
			
			session.setAttribute("corporator", corporatorDto);
			// ���O�C�������O�̃y�[�W���ɑJ�ڂ���
			response.sendRedirect("list-user");
				
		} catch (SQLException | NamingException e) {
			// ��O���b�Z�[�W���o�͕\��
			e.printStackTrace();
				
			//�V�X�e���G���[�ɑJ�ڂ���
			response.sendRedirect("corporator-system-error.jsp");
		}
	} 
}

