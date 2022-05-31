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
import jp.motors.FieldValidator;
import jp.motors.dao.ProblemDao;
import jp.motors.dao.SelectionResultDao;
import jp.motors.dto.OperatorDto;
import jp.motors.dto.ProblemDto;
import jp.motors.dto.SelectionResultDto;

/**
 * Servlet implementation class EdtiProblem
 */
@WebServlet("/edit-problem")
public class EditProblemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Z�b�V�����܂��̓��O�C���������ĂȂ�������A���O�C����ʂɑJ�ڂ���
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			response.sendRedirect("login");
			return;
		}
		
		//�@�R�[�XID���擾����
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		//�@�R�[�XID�Ɋ֘A������H��肪�����Ă��邩����
		try (Connection conn = DataSourceManager.getConnection()) {
			
			//�R�[�XID�Ɋ֘A������H�����擾
			ProblemDao problemDao = new ProblemDao(conn);
			List<ProblemDto> problemList = new ArrayList<>();
			
			if (problemDao.selectCountByCourseId(courseId) == 0) {
				Boolean createFlg = true;
				request.setAttribute("createFlg", createFlg);
			}
			
			if (session.getAttribute("problemList") == null) {
				problemList = problemDao.selectByCourseId(courseId);
			} else {
				problemList.addAll((List<ProblemDto>)session.getAttribute("problemList"));
			}
			
			if (problemList.size() == 0) {
				
				for (int i = 0; i < 10; i ++) {
					ProblemDto problemDto = new ProblemDto();
					problemDto.setProblemId(0);
					problemDto.setCourseId(courseId);
					problemDto.setProblem(null);
					problemDto.setProblemStatement(null);
					problemDto.setCorrectSelectId(0);
					
					List<SelectionResultDto> selectionResultList = new ArrayList<>();
					for (int j = 0; j < 4; j ++) {
						SelectionResultDto selectionResultDto = new SelectionResultDto();
						selectionResultDto.setResult(null);
						selectionResultDto.setSelection(null);
						selectionResultDto.setProblemId(0);
						selectionResultDto.setProblemId(0);
						selectionResultList.add(selectionResultDto);
					}
					problemDto.setSelectionResultList(selectionResultList);
					problemList.add(problemDto);
				}
				
			}
			
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessageList");
			
			request.setAttribute("courseId", courseId);
			
			request.setAttribute("problemList", problemList);
			
			request.getRequestDispatcher("WEB-INF/edit-problem.jsp").forward(request, response);
			
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("operator-system-error.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Z�b�V�����܂��̓��O�C���������ĂȂ�������A���O�C����ʂɑJ�ڂ���
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			response.sendRedirect("login");
			return;
		}
		
		OperatorDto operator = (OperatorDto)session.getAttribute("operator");
		
		request.setCharacterEncoding("UTF-8");
		
		//�@�R�[�XID���擾����
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		//�@�f�[�^���擾����
		try (Connection conn = DataSourceManager.getConnection()) {
			
			conn.setAutoCommit(false);
			
			ProblemDao problemDao = new ProblemDao(conn);
			List<String> errorMessageList = new ArrayList<>();
			List<ProblemDto> problemList = new ArrayList<>();
			for (int problemNumber = 1; problemNumber <= 10; problemNumber++) {
				
				ProblemDto problemDto = new ProblemDto();
				problemDto.setCourseId(courseId);
				problemDto.setProblem(request.getParameter("problem" + problemNumber));
				problemDto.setProblemStatement(request.getParameter("problemStatement" + problemNumber));
				problemDto.setUpdateOperatorId(operator.getUserId());
				if (request.getParameter("correctSelection" + problemNumber) != null) {
					problemDto.setCorrectSelectId(Integer.parseInt(request.getParameter("correctSelection" + problemNumber)));
				}
				if (request.getParameter("problemId" + problemNumber) != null) {
					problemDto.setProblemId(Integer.parseInt(request.getParameter("problemId" + problemNumber)));
				}
				
				// ���̓`�F�b�N
				errorMessageList.addAll(FieldValidator.problemValidation(problemDto, problemNumber));
				
				List<SelectionResultDto> selectionResultList = new ArrayList<>();
				for (int selectionResultNumber = 1; selectionResultNumber <= 4 ; selectionResultNumber++) {
					
					SelectionResultDto selectionResultDto = new SelectionResultDto();
					if (request.getParameter("problemId" + problemNumber) != null) {
						selectionResultDto.setProblemId(Integer.parseInt(request.getParameter("problemId" + problemNumber)));
					} 
					if (request.getParameter("selectionResultId" + Integer.toString(problemNumber) + Integer.toString(selectionResultNumber)) != null) {
						selectionResultDto.setSelectionResultId(Integer.parseInt(request.getParameter("selectionResultId" + Integer.toString(problemNumber) + Integer.toString(selectionResultNumber))));
					}
					selectionResultDto.setSelection(request.getParameter("selection" + Integer.toString(problemNumber) + Integer.toString(selectionResultNumber)));
					selectionResultDto.setResult(request.getParameter("result" + Integer.toString(problemNumber) + Integer.toString(selectionResultNumber)));
					// ���̓`�F�b�N
					errorMessageList.addAll(FieldValidator.SelectionResultValidation(selectionResultDto, problemNumber, selectionResultNumber));
					selectionResultList.add(selectionResultDto);
					
				}
				problemDto.setSelectionResultList(selectionResultList);
				problemList.add(problemDto);
			}
			
			if (errorMessageList.size() != 0) {
				
				conn.rollback();
				
				session.setAttribute("errorMessageList", errorMessageList);
				
				session.setAttribute("problemList", problemList);
				
				response.sendRedirect("edit-problem?courseId=" + courseId);
				return;
			}
			
			// �R�[�XID�Ɋ֘A������H�����擾
			List<ProblemDto> list = problemDao.selectByCourseId(courseId);
			
			if (list.size() == 0) {
				
				int count1 = 0;
				//�@�쐬����
				for (ProblemDto problemDto : problemList) {
					count1 += problemDao.insert(problemDto);
					
					SelectionResultDao selectionResultDao = new SelectionResultDao(conn);
					List<SelectionResultDto> selectionResultList = problemDto.getSelectionResultList();
					
					for (SelectionResultDto dto : selectionResultList) {
						dto.setProblemId(problemDao.selectMaxProblemId());
						count1 += selectionResultDao.insert(dto);
					}
					
					int correctSelectionId = selectionResultDao.selectCorrectSelectionId(problemDto.getCorrectSelectId());
					
					problemDto.setCorrectSelectId(correctSelectionId);
					problemDto.setProblemId(problemDao.selectMaxProblemId());
					
					//�@SQL�����s����
					count1 += problemDao.updateCorrectSelectionResultId(problemDto);
					
					}
				if (count1 == 60) {
					conn.setAutoCommit(true);
					session.setAttribute("message", "���H�����쐬���܂���");
				} else {
					conn.rollback();
					session.setAttribute("message", "���H��肪�쐬�ł��܂���ł���");
				}
				
			// �X�V
			} else {
				
				int count2 = 0;
				//�@�X�V�҂�����
				for (ProblemDto problemDto : problemList) {
					if (problemDao.selectCorrectSelectionByProblemId(problemDto.getProblemId()) != problemDto.getCorrectSelectId()) {
						int correctSelectId = problemDao.selectCorrectSelectionByProblemId(problemDto.getProblemId()) - Integer.parseInt(request.getParameter("correctSelectIdNumber")) + problemDto.getCorrectSelectId();
						problemDto.setCorrectSelectId(correctSelectId);
					}
					
					count2 += problemDao.update(problemDto);
					SelectionResultDao selectionResultDao = new SelectionResultDao(conn);
					List<SelectionResultDto> selectionResultList = problemDto.getSelectionResultList();
					
					for (SelectionResultDto dto : selectionResultList) {
						count2 += selectionResultDao.update(dto);
					}
				}
				
				if (count2 == 50) {
					conn.setAutoCommit(true);
					session.setAttribute("message", "���H�����X�V���܂���");
				} else {
					conn.rollback();
					session.setAttribute("message", "���H��肪�X�V�ł��܂���ł���");
				}
				
			}
			
			response.sendRedirect("list-course-operator");

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("operator-system-error.jsp");
		}
		
	}

}
