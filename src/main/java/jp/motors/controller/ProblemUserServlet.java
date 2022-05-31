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
import jp.motors.dao.ProblemDao;
import jp.motors.dao.ResultDao;
import jp.motors.dto.ProblemDto;
import jp.motors.dto.ResultDto;
import jp.motors.dto.UserDto;



@WebServlet("/problem-user")
public class ProblemUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Z�b�V�����J�n
		HttpSession session = request.getSession(true);
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			if (request.getParameter("finishFlg") != null && session.getAttribute("user") == null) {
				response.sendRedirect("list-course");
				return;
			}
			
			//�@finishFLg���m�F����
			if (request.getParameter("finishFlg") != null) {
				List<ResultDto> resultList = (List<ResultDto>)session.getAttribute("resultList");
				for (ResultDto dto : resultList) {
					ResultDao resultDao = new ResultDao(conn);
					resultDao.insert(dto);
				}
				response.sendRedirect("list-course");
				return;
			}
			
			//�@�R�[�XID���擾����
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			
			// �Z�b�V�����ɏ�񂪓����Ă��Ȃ������烊�_�C���N�g	
//			if (session.getAttribute("user") == null) {
//				//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
//				request.getRequestDispatcher("index.jsp").forward(request, response);
//				return;
//			} 
		
			ProblemDao problemDao = new ProblemDao(conn);
			List<ProblemDto> problemList = new ArrayList<>();
			problemList = problemDao.selectByCourseId(courseId);

			List<String> errorMessageList = new ArrayList<>();
			if (problemList.size() == 0) {
				errorMessageList.add("���̃R�[�X�̎��H��肪�󂯂��܂���");
				errorMessageList.add("��ς��萔�ł����A�Ǘ��҂܂ł��₢���킹��������");
				session.setAttribute("errorMessageList", errorMessageList);
				response.sendRedirect("list-course");
				return;
			}
			
			//�@���ԍ����쐬/�擾����
			int problemNumber = 0;
			if (request.getParameter("problemNumber") != null) {
				problemNumber = Integer.parseInt(request.getParameter("problemNumber")) + 1;
			}
			
			ProblemDto problemDto = new ProblemDto();
			problemDto = problemList.get(problemNumber);
			
			//�@���ԍ������N�G�X�g�ɕێ�����
			request.setAttribute("problemNumber", problemNumber);
			
			// �������N�G�X�g�X�R�[�v�ɃZ�b�g
			request.setAttribute("problemDto", problemDto);
			
			// problem-user�ɑJ�ڂ���
			request.getRequestDispatcher("WEB-INF/problem-user.jsp").forward(request, response);
			
		} catch (SQLException | NamingException e) {
			//�G���[����ʂɏo��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ��
			response.sendRedirect("user-system-error.jsp");
		}
	}

	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
		
		// �Z�b�V�����ɏ�񂪓����Ă��Ȃ������烊�_�C���N�g	
		if (session == null ) {
			//�R�[�X�ꗗ�y�[�W�ɑJ�ڂ���
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		//�@�R�[�XID���擾����
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		//�@���ԍ����쐬/�擾����
		int problemNumber = Integer.parseInt(request.getParameter("problemNumber"));
		
		if (request.getParameter("selection") == null) {
			request.setAttribute("message", "�I������I�����Ă�������");
			
			try (Connection conn = DataSourceManager.getConnection()) {
				
				ProblemDao problemDao = new ProblemDao(conn);
				List<ProblemDto> problemList = new ArrayList<>();
				problemList = problemDao.selectByCourseId(courseId);
				
				//�@�����擾����
				ProblemDto problemDto = new ProblemDto();
				problemDto = problemList.get(problemNumber);
	
				//�@���ԍ������N�G�X�g�ɕێ�����
				request.setAttribute("problemNumber", problemNumber);
				
				// �������N�G�X�g�X�R�[�v�ɃZ�b�g
				request.setAttribute("problemDto", problemDto);
				
				// problem-user�ɑJ�ڂ���
				request.getRequestDispatcher("WEB-INF/problem-user.jsp").forward(request, response);
				return;
				
			} catch (SQLException | NamingException e) {
				//�G���[����ʂɏo��
				e.printStackTrace();
				//�V�X�e���G���[��ʂɑJ��
				response.sendRedirect("user-system-error.jsp");
			}
		}
		
		if (session.getAttribute("user") == null) {
			// courseId�ɕR�Â���problemList�擾����
			try (Connection conn = DataSourceManager.getConnection()) {
				
				ProblemDao problemDao = new ProblemDao(conn);
				List<ProblemDto> problemList = new ArrayList<>();
				problemList = problemDao.selectByCourseId(courseId);
				
				//�@�����擾����
				ProblemDto problemDto = new ProblemDto();
				problemDto = problemList.get(problemNumber);
	
				//�@���ԍ������N�G�X�g�ɕێ�����
				request.setAttribute("problemNumber", problemNumber);
				
				// �������N�G�X�g�X�R�[�v�ɃZ�b�g
				request.setAttribute("problemDto", problemDto);
				
				request.setAttribute("selection", Integer.parseInt(request.getParameter("selection")));
				
				// problem-user�ɑJ�ڂ���
				request.getRequestDispatcher("WEB-INF/problem-user.jsp").forward(request, response);
				return;
				
			} catch (SQLException | NamingException e) {
				//�G���[����ʂɏo��
				e.printStackTrace();
				//�V�X�e���G���[��ʂɑJ��
				response.sendRedirect("user-system-error.jsp");
			}
		
		}
		
		//�@User�擾����
		UserDto user = new UserDto();
		ResultDto resultDto = new ResultDto();
		if (session.getAttribute("user") != null) {
			user = (UserDto)session.getAttribute("user");
			
			// �t�H�[���̃f�[�^���擾����
			request.setCharacterEncoding("UTF-8");
			resultDto.setUserId(user.getUserId());
			resultDto.setProblemId(Integer.parseInt(request.getParameter("problemId")));
			resultDto.setSelectedSelectionId(Integer.parseInt(request.getParameter("selection")));

		}
		
		// courseId�ɕR�Â���problemList�擾����
		try (Connection conn = DataSourceManager.getConnection()) {
			
			ProblemDao problemDao = new ProblemDao(conn);
			List<ProblemDto> problemList = new ArrayList<>();
			problemList = problemDao.selectByCourseId(courseId);
			
			//�@���H��茋�ʂ�list�ɓ����
			List<ResultDto> resultList = new ArrayList<>();
			if (session.getAttribute("resultList") != null) {
				resultList = (List<ResultDto>)session.getAttribute("resultList");
			}
			resultList.add(resultDto);
			
			//�@�����擾����
			ProblemDto problemDto = new ProblemDto();
			problemDto = problemList.get(problemNumber);

			//�@����List�����N�G�X�g�ɕێ�����
			session.setAttribute("resultList", resultList);
			
			//�@���ʂ����N�G�X�g�ɕێ�����
			request.setAttribute("resultDto", resultDto);
			
			//�@���ԍ������N�G�X�g�ɕێ�����
			request.setAttribute("problemNumber", problemNumber);
			
			// �������N�G�X�g�X�R�[�v�ɃZ�b�g
			request.setAttribute("problemDto", problemDto);
			
			// problem-user�ɑJ�ڂ���
			request.getRequestDispatcher("WEB-INF/problem-user.jsp").forward(request, response);
		
		} catch (SQLException | NamingException e) {
			//�G���[����ʂɏo��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ��
			response.sendRedirect("user-system-error.jsp");
		}
	}

}
