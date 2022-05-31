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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class ResetPasswordOperator
 */
@WebServlet(urlPatterns={"/reset-password-operator"},initParams={@WebInitParam(name="password", value="codetrain123")})
public class ResetPasswordOperator extends HttpServlet {
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
		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setOperatorId(Integer.parseInt(request.getParameter("operatorId")));
		operatorDto.setUpdateOperatorId(operator.getOperatorId());
		operatorDto.setPassword(getInitParameter("password"));
		
		//����������
		try (Connection conn = DataSourceManager.getConnection()) {
			OperatorDao operatorDao = new OperatorDao(conn);
			operatorDao.resetPassword(operatorDto);
			
			//�X�V�����|�̃��b�Z�[�W�i�p�X���[�h�����������܂����j���Z�b�V�����X�R�[�v�ɕێ�����
			session.setAttribute("message", "�p�X���[�h�����������܂���");
			
			//ListUserServlet�Ƀ��_�C���N�g
			response.sendRedirect("operator-list");
		
		} catch (SQLException | NamingException e) {
			//�G���[����ʂɏo��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ��
			response.sendRedirect("operator-system-error.jsp");
			
		}
	}
		

}
