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
import jp.motors.dao.UserDao;
import jp.motors.dto.UserDto;

//�@�����p�X���[�h��initParams�Ɋi�[����
@WebServlet(urlPatterns={"/user-login"}, initParams={@WebInitParam(name="password", value="codetrain123")})
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// �g�b�v�y�[�W(�R�[�X�ꗗ)�ɑJ�ڂ���
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// �t�H�[���̃f�[�^���擾����
		String loginId = request.getParameter("id");
		String loginPassword = request.getParameter("password");
		String uri = request.getParameter("uri");

		// �Z�b�V�������擾����
		HttpSession session = request.getSession(true);

		// ���O�C��ID�A�p�X���[�h�������͂̏ꍇ
		if ("".equals(loginId) || "".equals(loginPassword)) {

			session.setAttribute("navbarMessage", "���[���A�h���X�A�p�X���[�h����͂��Ă�������");
			
			// ���O�C�������O�̃y�[�W���ɑJ�ڂ���
			response.sendRedirect(uri);
			return;
		}
		
		try (Connection conn = DataSourceManager.getConnection()) {

			// ���O�C������
			UserDao loginDao = new UserDao(conn);
			UserDto userDto = loginDao.findByIdAndPassword(loginId, loginPassword);
			
			session.removeAttribute("navbarMessage");

			// ���O�C�����s��
			if (userDto == null) {
				session.setAttribute("navbarMessage", "���[���A�h���X�܂��̓p�X���[�h���Ԉ���Ă��܂�");
			}
			
			
			// ������������30�����Ȃ���
			boolean resetFlg = loginDao.resetCheck(loginId, loginPassword);
			
			//�@�������������߂��Ă���ꍇ
			if (getInitParameter("password").equals(loginPassword) && !resetFlg) {
				session.setAttribute("navbarMessage", "�������������߂��Ă��܂�");
				response.sendRedirect(uri);
				return;
			}
			
			// ���񃍃O�C����&�����������̂R�O���ȓ�
			if (getInitParameter("password").equals(loginPassword) && resetFlg) {
				session.setAttribute("user", userDto);
				session.setAttribute("isChangeRequired", true);
				response.sendRedirect("change-user-password");
				return;
			}
			
			session.setAttribute("user", userDto);
			// ���O�C�������O�̃y�[�W���ɑJ�ڂ���
			response.sendRedirect(uri);
			
		} catch (SQLException | NamingException e) {
			// ��O���b�Z�[�W���o�͕\��
			e.printStackTrace();
			
			// �V�X�e���G���[�ɑJ�ڂ���
			response.sendRedirect("user-system-error.jsp");
		}
	}
}
