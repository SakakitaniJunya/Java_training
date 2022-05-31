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
		// セッションまたはログインが入ってなかったら、ログイン画面に遷移する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			response.sendRedirect("login");
			return;
		}
		
		//　コースIDを取得する
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		//　コースIDに関連する実践問題が入っているか分岐
		try (Connection conn = DataSourceManager.getConnection()) {
			
			//コースIDに関連する実践問題を取得
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
		// セッションまたはログインが入ってなかったら、ログイン画面に遷移する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			response.sendRedirect("login");
			return;
		}
		
		OperatorDto operator = (OperatorDto)session.getAttribute("operator");
		
		request.setCharacterEncoding("UTF-8");
		
		//　コースIDを取得する
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		//　データを取得する
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
				
				// 入力チェック
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
					// 入力チェック
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
			
			// コースIDに関連する実践問題を取得
			List<ProblemDto> list = problemDao.selectByCourseId(courseId);
			
			if (list.size() == 0) {
				
				int count1 = 0;
				//　作成する
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
					
					//　SQLを実行する
					count1 += problemDao.updateCorrectSelectionResultId(problemDto);
					
					}
				if (count1 == 60) {
					conn.setAutoCommit(true);
					session.setAttribute("message", "実践問題を作成しました");
				} else {
					conn.rollback();
					session.setAttribute("message", "実践問題が作成できませんでした");
				}
				
			// 更新
			} else {
				
				int count2 = 0;
				//　更新者を入れる
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
					session.setAttribute("message", "実践問題を更新しました");
				} else {
					conn.rollback();
					session.setAttribute("message", "実践問題が更新できませんでした");
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
