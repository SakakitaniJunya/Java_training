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

import jp.motors.FieldValidator;
import jp.motors.DataSourceManager;
import jp.motors.dao.CategoryDao;
import jp.motors.dto.CategoryDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/edit-category")
public class EditCategoryServlet extends HttpServlet {
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
		
		CategoryDto categoryDto = (CategoryDto) session.getAttribute("categoryDto");
		
		if (categoryDto != null) {
			request.setAttribute("categoryDto", categoryDto);
			session.removeAttribute("categoryDto");
		} else {
			request.setCharacterEncoding("UTF-8");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			
			try (Connection conn = DataSourceManager.getConnection()) {
				
				CategoryDao categoryDao = new CategoryDao(conn);
				
				categoryDto = categoryDao.selectByCategoryId(categoryId);
				
				request.setAttribute("categoryDto", categoryDto);
				
				if (operator.getAuthority() == 1) {
					response.sendRedirect("access-denied.jsp");
					return;
				}
				
			} catch (SQLException | NamingException e) {
				// ��O���b�Z�[�W���o�͕\��
				e.printStackTrace();

				// �V�X�e���G���[��ʂɑJ�ڂ���
				response.sendRedirect("operator-system-error.jsp");
				return;
			}
			
			request.getRequestDispatcher("WEB-INF/edit-category.jsp").forward(request, response);
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
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
		categoryDto.setCategory(request.getParameter("category"));
		categoryDto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));
		
		// ���̓`�F�b�N
		List<String> errorMessageList = FieldValidator.categoryValidation(categoryDto);
		if (errorMessageList.size() != 0) {
			// �`�����l���ҏW��ʂɑJ�ڂ���
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("edit-category?categoryId=" + request.getParameter("categoryId"));
			return;
		}
		
		// �R�l�N�V�������擾����
		try (Connection conn = DataSourceManager.getConnection()){
			
			CategoryDao categoryDao = new CategoryDao(conn);
			
			if (operator.getAuthority() == 1) {
				response.sendRedirect("access-denied.jsp");
				return;
			}
			
			categoryDao.update(categoryDto);
			
			session.setAttribute("message", "�J�e�S�����X�V���܂���");
			
			response.sendRedirect("list-course-operator");
			
			
		} catch (SQLException | NamingException e) {
			// �J�e�S�������d�����Ă���ꍇ
			if (e.getMessage().contains("Duplicate entry")) {

				// �G���[���b�Z�[�W�����N�G�X�g�X�R�[�v�ɕێ�����
				errorMessageList.add("�J�e�S���u" + categoryDto.getCategory() + "�v�͊��ɑ��݂��܂�");
				session.setAttribute("errorMessageList", errorMessageList);

				// �t�H�[���̃f�[�^�����N�G�X�g�X�R�[�v�ɕێ�����
				session.setAttribute("categoryDto", categoryDto);

				// �J�e�S���ҏW��ʂɑJ�ڂ���
				response.sendRedirect("edit-category?categoryId=" + request.getParameter("categoryId"));
			} else {
				// ��O���b�Z�[�W���o�͕\��
				e.printStackTrace();
				
				// �V�X�e���G���[��ʂɑJ�ڂ���
				response.sendRedirect("operator-system-error.jsp");
			}
		
		}
		
	}

}
