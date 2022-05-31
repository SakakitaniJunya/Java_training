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
import jp.motors.dao.CourseDao;
import jp.motors.dao.IndexDao;
import jp.motors.dto.CourseDto;
import jp.motors.dto.IndexDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/create-text")
public class CreateTextServlet extends HttpServlet {
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
			
			// courseId�����N�G�X�g�X�R�[�v�ɕێ�����
			CourseDao courseDao = new CourseDao(conn);
			CourseDto courseDto = new CourseDto();
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			courseDto.setCourseId(courseId);
			courseDto = courseDao.selectByCourseId(courseId);	
			
			request.setAttribute("courseDto", courseDto);
			
			// �G���[���b�Z�[�W�����N�G�X�g�ɕێ�����
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessage");			
			
			request.getRequestDispatcher("WEB-INF/create-text.jsp").forward(request, response);
			
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
		IndexDto indexDto = new IndexDto();
		indexDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));
		indexDto.setIndexs(request.getParameter("indexs"));
		indexDto.setContent(request.getParameter("content"));
		indexDto.setUpdateOperatorId(operator.getUpdateOperatorId());
		
		// ���̓`�F�b�N
		List<String> errorMessageList = FieldValidator.indexValidation(indexDto);
		if (errorMessageList.size() != 0) {
			// �`�����l���o�^��ʂɑJ�ڂ���
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("create-text");
			return;
		}		
		
		try (Connection conn = DataSourceManager.getConnection()){
			IndexDao indexDao = new IndexDao(conn);
			
				// text���쐬����
				indexDao.insert(indexDto);
				
				// text�����N�G�X�g�X�R�[�v�ɕێ�����
				session.setAttribute("message", indexDto.getIndexs() + "��o�^���܂���");
				
				// text�ꗗ��ʂɑJ�ڂ���
				response.sendRedirect("list-text?courseId=" + indexDto.getCourseId());
			
			
		} catch (SQLException | NamingException e) {
			
	
				// ��O���b�Z�[�W���o�͕\��
				e.printStackTrace();
				
				// �V�X�e���G���[��ʂɑJ�ڂ���
				response.sendRedirect("operator-system-error.jsp");
		}
		
	}

}
