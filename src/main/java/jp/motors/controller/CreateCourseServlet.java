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


@WebServlet("/create-course")
public class CreateCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		// �R�l�N�V�������擾����
		try (Connection conn = DataSourceManager.getConnection()) {
			// �Z�b�V�������擾����
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("operator") == null) {
				// �R�[�X�ꗗ�ɑJ�ڂ���
				response.sendRedirect("login");
				return;
			}
		
			CategoryDao categoryDao = new CategoryDao(conn);
			List<CategoryDto> categoryList = categoryDao.selectAll();
		
			request.setAttribute("categoryList", categoryList);
		
			// �G���[���b�Z�[�W�����N�G�X�g�ɕێ�����
			request.setAttribute("MessageList", session.getAttribute("MessageList"));
			session.removeAttribute("MessageList");
						
			// �J�e�S���쐬��ʂɑJ�ڂ���
			request.getRequestDispatcher("WEB-INF/create-course.jsp").forward(request, response);
		} catch (SQLException | NamingException e) {
			
			// ��O���b�Z�[�W���o�͕\��
			e.printStackTrace();
						
			// �V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("operator-system-error.jsp");
		}
		
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
		courseDto.setCourse(request.getParameter("course"));
		if (request.getParameter("estimatedStudyTime") != "") {
			courseDto.setEstimatedStudyTime(Integer.parseInt(request.getParameter("estimatedStudyTime")));
		} 
		courseDto.setCourseOverview(request.getParameter("courseOverview"));
		courseDto.setPrerequisite(request.getParameter("prerequisite"));
		courseDto.setGoal(request.getParameter("goal"));
		courseDto.setUpdateOperatorId(operator.getUpdateOperatorId());
		
		// ���̓`�F�b�N
		List<String> errorMessageList = FieldValidator.courseValidation(courseDto);
		if (errorMessageList.size() != 0) {
			// �`�����l���o�^��ʂɑJ�ڂ���
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("create-course");
			return;
		}
		
		try (Connection conn = DataSourceManager.getConnection()){
			// �J�e�S�����쐬����
			CourseDao courseDao = new CourseDao(conn);
			courseDao.insert(courseDto);
			
			// �J�e�S�������N�G�X�g�X�R�[�v�ɕێ�����
			session.setAttribute("message", courseDto.getCourse() + "��o�^���܂���");
			
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
