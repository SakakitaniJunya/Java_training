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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class DeleteOperatorServlet
 */
@WebServlet(name = "delete-operator", urlPatterns = { "/delete-operator" })
public class DeleteOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// login�Ƀ��_�C���N�g����
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
		
		//�@�t�H�[���̃f�[�^�i�^�p��ID�j���擾����
		request.setCharacterEncoding("UTF-8");
		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setOperatorId(Integer.parseInt(request.getParameter("operatorId")));
		operatorDto.setUpdateOperatorId(operator.getOperatorId());
		

		try (Connection conn = DataSourceManager.getConnection()) {

			//�f�[�^�x�[�X�ڑ�
			OperatorDao operatorDao = new OperatorDao(conn);
				
			//�폜
			operatorDao.deleteDelFlg(operatorDto);
			
			session.setAttribute("message", "�^�p�҂��폜���܂���");
			//7. ListUserServlet�Ƀ��_�C���N�g
			response.sendRedirect("operator-list");
			
			
		} catch (SQLException | NamingException e) {
			//�G���[���o�͕\��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("operator-system-error.jsp");
			
		}
		
	}

}
