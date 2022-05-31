package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.FieldValidator;
import jp.motors.dao.CategoryDao;
import jp.motors.dao.CourseDao;
import jp.motors.dto.CategoryDto;
import jp.motors.dto.CourseDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/edit-course")
public class EditCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Z�b�V�������擾����
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			// �R�[�X�ꗗ�ɑJ�ڂ���
			response.sendRedirect("login");
			return;
		}
		
		// �Z�b�V��������Operator�����擾����
		OperatorDto operator = (OperatorDto)session.getAttribute("operator");		
		
		// �G���[���b�Z�[�W�����N�G�X�g�ɕێ�����
		request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
		session.removeAttribute("errorMessageList");
		
		CourseDto courseDto = (CourseDto) session.getAttribute("courseDto");
		
		if (courseDto != null) {
			session.removeAttribute("courseDto");
		} else {
			request.setCharacterEncoding("UTF-8");
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			
			try (Connection conn = DataSourceManager.getConnection()){
				
				CourseDao courseDao = new CourseDao(conn);
				
				courseDto = courseDao.selectByCourseId(courseId);
				
				request.setAttribute("courseDto", courseDto);
				
				if (operator.getAuthority() == 1) {
					response.sendRedirect("access-denied.jsp");
					return;
				}	
				
			}catch (SQLException | NamingException e) {
				// ��O���b�Z�[�W���o�͕\��
				e.printStackTrace();

				// �V�X�e���G���[��ʂɑJ�ڂ���
				response.sendRedirect("operator-system-error.jsp");
				return;
			}
		
		}
		
		try (Connection conn = DataSourceManager.getConnection()){
			
			// �`�����l�������擾����
			CategoryDao categoryDao = new CategoryDao(conn);
			List<CategoryDto> categoryList = categoryDao.selectAll();
			
			request.setAttribute("categoryList", categoryList);
		
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			
			response.sendRedirect("operator-system-error.jsp");
			return;
		}
		
		request.getRequestDispatcher("WEB-INF/edit-course.jsp").forward(request, response);

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
		courseDto.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
		courseDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));
		courseDto.setCourse(request.getParameter("course"));
		courseDto.setEstimatedStudyTime(Integer.parseInt(request.getParameter("estimatedStudyTime")));
		courseDto.setCourseOverview(request.getParameter("courseOverview"));
		courseDto.setPrerequisite(request.getParameter("prerequisite"));
		courseDto.setGoal(request.getParameter("goal"));		
		courseDto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));
		
		// ���̓`�F�b�N
		List<String> errorMessageList = FieldValidator.courseValidation(courseDto);
		if (errorMessageList.size() != 0) {
			// �`�����l���o�^��ʂɑJ�ڂ���
			session.setAttribute("errorMessageList", errorMessageList);
			session.setAttribute("courseDto", courseDto);
			response.sendRedirect("edit-course?courseId=" + request.getParameter("courseId"));
			return;
		}		
		
		// �R�l�N�V�������擾����
		try (Connection conn = DataSourceManager.getConnection()){
			
			CourseDao courseDao = new CourseDao(conn);
			
			if (operator.getAuthority() == 1) {
				response.sendRedirect("access-denied.jsp");
				return;
			}
			
			courseDao.update(courseDto);
			
			session.setAttribute("message", "�R�[�X���X�V���܂���");
			
			response.sendRedirect("list-course-operator");
			
			
		} catch (SQLException | NamingException e) {
			// �J�e�S�������d�����Ă���ꍇ
			if (e.getMessage().contains("Duplicate entry")) {

				// �G���[���b�Z�[�W�����N�G�X�g�X�R�[�v�ɕێ�����
				errorMessageList.add("�R�[�X�u" + courseDto.getCourse() + "�v�͊��ɑ��݂��܂�");
				session.setAttribute("errorMessageList", errorMessageList);

				// �t�H�[���̃f�[�^�����N�G�X�g�X�R�[�v�ɕێ�����
				session.setAttribute("courseDto", courseDto);

				// �J�e�S���ҏW��ʂɑJ�ڂ���
				response.sendRedirect("edit-course?courseId=" + request.getParameter("courseId"));
			} else {
				// ��O���b�Z�[�W���o�͕\��
				e.printStackTrace();
				
				// �V�X�e���G���[��ʂɑJ�ڂ���
				response.sendRedirect("operator-system-error.jsp");
			}
		
		}		
		
	}

}
