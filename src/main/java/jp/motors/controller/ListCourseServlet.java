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
import jp.motors.dao.ProblemDao;
import jp.motors.dao.ResultDao;
import jp.motors.dto.CourseDto;
import jp.motors.dto.ResultDto;
import jp.motors.dto.UserDto;

/**
 * Servlet implementation class ListCourseServlet
 */
@WebServlet("/list-course")
public class ListCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �R�l�N�V�������擾����
		try (Connection conn = DataSourceManager.getConnection()) {
			// �Z�b�V�����̎擾����
			HttpSession session = request.getSession(true);
			
			// �Z�b�V�������烊�U���g���폜����
			session.removeAttribute("resultList");
			
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
			
			if (session.getAttribute("user") != null) {
				ResultDao resultDao = new ResultDao(conn);
				ProblemDao problemDao = new ProblemDao(conn);
				
				// ���[�U���擾����
				UserDto user = (UserDto)session.getAttribute("user");
				
				for (CourseDto courseDto : courseList) {
					//�@���ʂ��擾����
					List<ResultDto> resultList = resultDao.selectByUserIdAndCourseId(user.getUserId(), courseDto.getCourseId());
					
					int score = 0;
					//�@�_�����v�Z����
						for (ResultDto resultDto : resultList){
							if (problemDao.selectCorrectSelectionByProblemId(resultDto.getProblemId()) == resultDto.getSelectedSelectionId()) {
								score += 10;
							}
						}
					courseDto.setScore(score);
				}
			}
			
			
			//�@�R�[�X�ꗗ�f�[�^�����N�G�X�g�ɕێ�����
			request.setAttribute("courseList", courseList);
			
			//�@URI�����N�G�X�g�ɕێ�����
			request.setAttribute("uri", request.getRequestURI());
			
			//�@�Z�b�V������message�����N�G�X�g�ɕێ�����A�Z�b�V��������폜����
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessageList");
			
			//�@�Z�b�V������message�����N�G�X�g�ɕێ�����A�Z�b�V��������폜����
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			//�@�Z�b�V������navbarmessage�����N�G�X�g�ɕێ����A�Z�b�V��������폜����
			request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			// �R�[�X�ꗗ�ɑJ�ڂ���
			request.getRequestDispatcher("WEB-INF/list-course.jsp").forward(request, response);
		
		} catch (SQLException | NamingException e) {
			
			e.printStackTrace();
			
			response.sendRedirect("user-system-error.jsp");
			
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
