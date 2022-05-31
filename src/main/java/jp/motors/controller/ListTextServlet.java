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
import jp.motors.dao.IndexDao;
import jp.motors.dto.IndexDto;


@WebServlet("/list-text")
public class ListTextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �R�l�N�V�������擾����
		try (Connection conn = DataSourceManager.getConnection()) {
			// �Z�b�V�����̎擾����
			HttpSession session = request.getSession(false);
			
			//�Z�b�V�������Ȃ��ꍇ�Alogin�ɑJ��
			if (session == null || session.getAttribute("operator") == null) {
			
			//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
			response.sendRedirect("login");
			return;
			}

			List<IndexDto> indexList = new ArrayList<>();
			
			int courseId = Integer.parseInt(request.getParameter("courseId"));
				
			IndexDao indexDao = new IndexDao(conn);
			indexList = indexDao.selectByCourseId(courseId);
			
			request.setAttribute("courseId", courseId);
			
			request.setAttribute("indexList", indexList);
			
			session.removeAttribute("indexDto");
			
			request.setAttribute("uri", request.getRequestURI());
			
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");		
			
			request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");			
			
			request.getRequestDispatcher("WEB-INF/list-text.jsp").forward(request, response);
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			
			response.sendRedirect("operator-system-error.jsp");			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
