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
import jp.motors.dao.IndexDao;
import jp.motors.dto.IndexDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/edit-text")
public class EditTextServlet extends HttpServlet {
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
		
		IndexDto indexDto = (IndexDto) session.getAttribute("indexDto");
		
		if (indexDto != null) {
			request.setAttribute("indexDto", indexDto);
			session.removeAttribute("indexDto");
		} else {
			request.setCharacterEncoding("UTF-8");
			int indexId = Integer.parseInt(request.getParameter("indexId"));
			
			try (Connection conn = DataSourceManager.getConnection()){
				
				IndexDao indexDao = new IndexDao(conn);
				indexDto = indexDao.selectByIndexId(indexId);
				
				request.setAttribute("indexDto", indexDto);
				
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
		}
			
		
		// �`�����l���ҏW��ʂɑJ�ڂ���
		request.getRequestDispatcher("WEB-INF/edit-text.jsp").forward(request, response);
		
		

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
		indexDto.setIndexId(Integer.parseInt(request.getParameter("indexId")));	
		indexDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));
		indexDto.setCourse(request.getParameter("course"));
		indexDto.setIndexs(request.getParameter("indexs"));
		indexDto.setContent(request.getParameter("content"));
		indexDto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));

		
		// ���̓`�F�b�N
		List<String> errorMessageList = FieldValidator.indexValidation(indexDto);
		if (errorMessageList.size() != 0) {
			// �`�����l���o�^��ʂɑJ�ڂ���
			session.setAttribute("errorMessageList", errorMessageList);
			session.setAttribute("indexDto", indexDto);
			response.sendRedirect("edit-text?indexId=" + request.getParameter("indexId"));
			return;
		}
		
		// �R�l�N�V�������擾����
		try (Connection conn = DataSourceManager.getConnection()){
			
			IndexDao indexDao = new IndexDao(conn);
			
			if (operator.getAuthority() == 1) {
				response.sendRedirect("access-denied.jsp");
				return;
			}
			
			indexDao.update(indexDto);
			
			session.setAttribute("message", "�e�L�X�g���X�V���܂���");
			
			response.sendRedirect("list-text?courseId=" + indexDto.getCourseId());
			
		} catch (SQLException | NamingException e) {

				// ��O���b�Z�[�W���o�͕\��
				e.printStackTrace();
				
				// �V�X�e���G���[��ʂɑJ�ڂ���
				response.sendRedirect("operator-system-error.jsp");
			
		
		}			
		
	}

}
