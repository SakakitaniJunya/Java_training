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
import jp.motors.dto.CategoryDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/create-category")
public class CreateCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Z�b�V�������擾����
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			// �R�[�X�ꗗ�ɑJ�ڂ���
			response.sendRedirect("login");
			return;
		}
		
		// �G���[���b�Z�[�W�����N�G�X�g�ɕێ�����
		request.setAttribute("MessageList", session.getAttribute("MessageList"));
		session.removeAttribute("MessageList");
				
		// �J�e�S���쐬��ʂɑJ�ڂ���
		request.getRequestDispatcher("WEB-INF/create-category.jsp").forward(request, response);
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
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategory(request.getParameter("category"));
		categoryDto.setUpdateOperatorId(operator.getUpdateOperatorId());
		
		// ���̓`�F�b�N
		List<String> errorMessageList = FieldValidator.categoryValidation(categoryDto);
		if (errorMessageList.size() != 0) {
			// �`�����l���o�^��ʂɑJ�ڂ���
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("create-category");
			return;
		}
		
		try (Connection conn = DataSourceManager.getConnection()){
			CategoryDao categoryDao = new CategoryDao(conn);
			
			CategoryDto checkCategoryDto = categoryDao.checkCategory("category");
			
			if (checkCategoryDto.getCategory() != null) {
				// �G���[���b�Z�[�W���Z�b�V�����X�R�[�v�ɕێ�����
				errorMessageList.add("�J�e�S���u" + categoryDto.getCategory() + "�v�͊��ɑ��݂��܂�");
				session.setAttribute("errorMessageList", errorMessageList);
				
				// �t�H�[���̃f�[�^���Z�b�V�����X�R�[�v�ɕێ�����
				session.setAttribute("categoryDto", categoryDto);

				// �J�e�S���쐬��ʂɑJ�ڂ���
				response.sendRedirect("create-category");
				return;
			} else {
				// �J�e�S�����쐬����
				categoryDao.insert(categoryDto);
				
				// �J�e�S�������N�G�X�g�X�R�[�v�ɕێ�����
				session.setAttribute("message", categoryDto.getCategory() + "��o�^���܂���");
				
				// �R�[�X�ꗗ��ʂɑJ�ڂ���
				response.sendRedirect("list-course-operator");
			}
			
		} catch (SQLException | NamingException e) {
			
			// �J�e�S�������d�����Ă���Ƃ�
			if (e.getMessage().contains("Duplicate entry")) {
	
				// ��O���b�Z�[�W���o�͕\��
				e.printStackTrace();
				
				// �V�X�e���G���[��ʂɑJ�ڂ���
				response.sendRedirect("operator-system-error.jsp");
			}

		}
		
	}

}
