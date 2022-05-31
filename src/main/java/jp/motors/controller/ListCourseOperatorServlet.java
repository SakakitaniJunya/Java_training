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
import jp.motors.dao.CourseDao;
import jp.motors.dto.CourseDto;


@WebServlet("/list-course-operator")
public class ListCourseOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �R�l�N�V�������擾����
		try (Connection conn = DataSourceManager.getConnection()) {
			// �Z�b�V�����̎擾����
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("operator") == null) {
				// �R�[�X�ꗗ�ɑJ�ڂ���
				response.sendRedirect("login");
				return;
			}		
			
			List<CourseDto> courseList = new ArrayList<>();
			if (request.getParameter("categoryId") == null) {
	
				//�@�R�[�X�ꗗ���擾����
				CourseDao courseDao = new CourseDao(conn);
				courseList = courseDao.selectAll();
				
				
			} else {
				
				// �J�e�S��ID���擾����
				int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			
				//�@�R�[�X�ꗗ���擾����
				CourseDao courseDao = new CourseDao(conn);
				courseList = courseDao.selectByCategoryId(categoryId);
				
			}
			
			//�@�R�[�X�ꗗ�f�[�^�����N�G�X�g�ɕێ�����
			session.setAttribute("courseList", courseList);
			
			//�@URI�����N�G�X�g�ɕێ�����
			request.setAttribute("uri", request.getRequestURI());
			
			//�@�Z�b�V������message�����N�G�X�g�ɕێ�����A�Z�b�V��������폜����
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			//�@�Z�b�V������message�����N�G�X�g�ɕێ�����A�Z�b�V��������폜����
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessageList");
			
			//�@�Z�b�V������navbarmessage�����N�G�X�g�ɕێ����A�Z�b�V��������폜����
			request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			// �R�[�X�ꗗ�ɑJ�ڂ���
			request.getRequestDispatcher("WEB-INF/list-course-operator.jsp").forward(request, response);
		
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
