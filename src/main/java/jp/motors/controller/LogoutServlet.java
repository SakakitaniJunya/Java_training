package jp.motors.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Z�b�V�������烆�[�U�����폜����
		HttpSession session = request.getSession();
		session.removeAttribute("corporator");
		session.removeAttribute("operator");
		//session�j�󂷂�
		session.invalidate();
		
		// login.jsp�ɑJ�ڂ���
		response.sendRedirect("login");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
