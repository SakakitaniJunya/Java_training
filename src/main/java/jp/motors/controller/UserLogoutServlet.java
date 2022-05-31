package jp.motors.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user-logout")
public class UserLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//get
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// �Z�b�V�������烆�[�U�����폜����
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		//session�j�󂷂�
		session.invalidate();
		
		// indx.jsp�ɑJ�ڂ���
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
