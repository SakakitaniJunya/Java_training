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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class ListOperatorServlet
 */
@WebServlet(name = "operator-list", urlPatterns = { "/operator-list" })
public class ListOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
				
				
		//�Z�b�V�������Ȃ��ꍇ�Alogin�ɑJ��
		if (session == null || session.getAttribute("operator") == null) {
		
		//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
		response.sendRedirect("login");
		return;
		}
	
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			//�^�p�ҏ��S���擾
			List<OperatorDto> operatorList =  new ArrayList<>(); 
			OperatorDao operatorDao = new OperatorDao(conn);
			operatorList = operatorDao.selectAll();
			
			//����������	
			List<OperatorDto> operatorList2 =  operatorDao.selectAll();
			List<Boolean> resetPasswordList = operatorDao.findResetPasswordById(operatorList2);
			request.setAttribute("resetPasswordList", resetPasswordList);
			
			int count = 0;
			for (OperatorDto operatordtos: operatorList) {
				operatordtos.setResetFlg(resetPasswordList.get(count));
				count++;
			}
			
			request.setAttribute("operatorList", operatorList);
			
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
			request.getRequestDispatcher("WEB-INF/list-operator.jsp").forward(request, response);
	
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
		doGet(request, response);
	}

}
