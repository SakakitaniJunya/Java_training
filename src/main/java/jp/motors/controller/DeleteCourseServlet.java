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
import jp.motors.dao.CourseDao;
import jp.motors.dao.IndexDao;
import jp.motors.dto.CourseDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/delete-course")
public class DeleteCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Z�b�V�������擾����
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			// �R�[�X�ꗗ�ɑJ�ڂ���
			response.sendRedirect("login");
			return;
		}
		
		// �Z�b�V��������Operator�����擾����
		OperatorDto operator = (OperatorDto)session.getAttribute("operator");
		
		// �t�H�[���̃f�[�^���擾����
		request.setCharacterEncoding("UTF-8");
		CourseDto courseDto = new CourseDto();
		courseDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));
		courseDto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));
		
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			try {
				
				// �g�����U�N�V�������J�n����
				conn.setAutoCommit(false);

				CourseDao courseDao = new CourseDao(conn);
				
				if (operator.getAuthority() == 1) {
					response.sendRedirect("access-denied.jsp");
					return;
				}
				
				// �R�[�X�����폜����
				courseDao.deleteByCourseId(courseDto);
				
				// �R�[�X�ɕR�Â��e�L�X�g���폜����
				IndexDao indexDao =new IndexDao(conn);
				indexDao.deleteByCourseId(courseDto);
				
				
				// �R�~�b�g����
				conn.commit();
				
				session.setAttribute("message", "�R�[�X���폜���܂���");
				
			} catch (SQLException e) {
			
				// ���[���o�b�N����
				conn.rollback();
				throw e;
		
			} finally {
				conn.setAutoCommit(true);
			}
			// �R�[�X�ꗗ��ʂɑJ�ڂ���
			response.sendRedirect("list-course-operator");
			
		} catch (SQLException | NamingException e) {
			
			// ��O���b�Z�[�W���o�͕\��
			e.printStackTrace();
			
			// �V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("operator-system-error.jsp");
		}		
		
	}

}
