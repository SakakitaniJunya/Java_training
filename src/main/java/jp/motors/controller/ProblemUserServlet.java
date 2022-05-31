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
		// セッション開始
		HttpSession session = request.getSession(true);
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			if (request.getParameter("finishFlg") != null && session.getAttribute("user") == null) {
				response.sendRedirect("list-course");
				return;
			}
			
			//　finishFLgを確認する
			if (request.getParameter("finishFlg") != null) {
				List<ResultDto> resultList = (List<ResultDto>)session.getAttribute("resultList");
				for (ResultDto dto : resultList) {
					ResultDao resultDao = new ResultDao(conn);
					resultDao.insert(dto);
				}
				response.sendRedirect("list-course");
				return;
			}
			
			//　コースIDを取得する
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			
			// セッションに情報が入っていなかったらリダイレクト	
//			if (session.getAttribute("user") == null) {
//				//コース一覧ページに遷移する
//				request.getRequestDispatcher("index.jsp").forward(request, response);
//				return;
//			} 
		
			ProblemDao problemDao = new ProblemDao(conn);
			List<ProblemDto> problemList = new ArrayList<>();
			problemList = problemDao.selectByCourseId(courseId);

			List<String> errorMessageList = new ArrayList<>();
			if (problemList.size() == 0) {
				errorMessageList.add("このコースの実践問題が受けられません");
				errorMessageList.add("大変お手数ですが、管理者までお問い合わせください");
				session.setAttribute("errorMessageList", errorMessageList);
				response.sendRedirect("list-course");
				return;
			}
			
			//　問題番号を作成/取得する
			int problemNumber = 0;
			if (request.getParameter("problemNumber") != null) {
				problemNumber = Integer.parseInt(request.getParameter("problemNumber")) + 1;
			}
			
			ProblemDto problemDto = new ProblemDto();
			problemDto = problemList.get(problemNumber);
			
			//　問題番号をリクエストに保持する
			request.setAttribute("problemNumber", problemNumber);
			
			// 問題をリクエストスコープにセット
			request.setAttribute("problemDto", problemDto);
			
			// problem-userに遷移する
			request.getRequestDispatcher("WEB-INF/problem-user.jsp").forward(request, response);
			
		} catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
			response.sendRedirect("user-system-error.jsp");
		}
	}

	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//セッション開始
		HttpSession session = request.getSession(false);
		
		// セッションに情報が入っていなかったらリダイレクト	
		if (session == null ) {
			//コース一覧ページに遷移する
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		//　コースIDを取得する
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		//　問題番号を作成/取得する
		int problemNumber = Integer.parseInt(request.getParameter("problemNumber"));
		
		if (request.getParameter("selection") == null) {
			request.setAttribute("message", "選択肢を選択してください");
			
			try (Connection conn = DataSourceManager.getConnection()) {
				
				ProblemDao problemDao = new ProblemDao(conn);
				List<ProblemDto> problemList = new ArrayList<>();
				problemList = problemDao.selectByCourseId(courseId);
				
				//　問題を取得する
				ProblemDto problemDto = new ProblemDto();
				problemDto = problemList.get(problemNumber);
	
				//　問題番号をリクエストに保持する
				request.setAttribute("problemNumber", problemNumber);
				
				// 問題をリクエストスコープにセット
				request.setAttribute("problemDto", problemDto);
				
				// problem-userに遷移する
				request.getRequestDispatcher("WEB-INF/problem-user.jsp").forward(request, response);
				return;
				
			} catch (SQLException | NamingException e) {
				//エラーを画面に出力
				e.printStackTrace();
				//システムエラー画面に遷移
				response.sendRedirect("user-system-error.jsp");
			}
		}
		
		if (session.getAttribute("user") == null) {
			// courseIdに紐づいたproblemList取得する
			try (Connection conn = DataSourceManager.getConnection()) {
				
				ProblemDao problemDao = new ProblemDao(conn);
				List<ProblemDto> problemList = new ArrayList<>();
				problemList = problemDao.selectByCourseId(courseId);
				
				//　問題を取得する
				ProblemDto problemDto = new ProblemDto();
				problemDto = problemList.get(problemNumber);
	
				//　問題番号をリクエストに保持する
				request.setAttribute("problemNumber", problemNumber);
				
				// 問題をリクエストスコープにセット
				request.setAttribute("problemDto", problemDto);
				
				request.setAttribute("selection", Integer.parseInt(request.getParameter("selection")));
				
				// problem-userに遷移する
				request.getRequestDispatcher("WEB-INF/problem-user.jsp").forward(request, response);
				return;
				
			} catch (SQLException | NamingException e) {
				//エラーを画面に出力
				e.printStackTrace();
				//システムエラー画面に遷移
				response.sendRedirect("user-system-error.jsp");
			}
		
		}
		
		//　User取得する
		UserDto user = new UserDto();
		ResultDto resultDto = new ResultDto();
		if (session.getAttribute("user") != null) {
			user = (UserDto)session.getAttribute("user");
			
			// フォームのデータを取得する
			request.setCharacterEncoding("UTF-8");
			resultDto.setUserId(user.getUserId());
			resultDto.setProblemId(Integer.parseInt(request.getParameter("problemId")));
			resultDto.setSelectedSelectionId(Integer.parseInt(request.getParameter("selection")));

		}
		
		// courseIdに紐づいたproblemList取得する
		try (Connection conn = DataSourceManager.getConnection()) {
			
			ProblemDao problemDao = new ProblemDao(conn);
			List<ProblemDto> problemList = new ArrayList<>();
			problemList = problemDao.selectByCourseId(courseId);
			
			//　実践問題結果をlistに入れる
			List<ResultDto> resultList = new ArrayList<>();
			if (session.getAttribute("resultList") != null) {
				resultList = (List<ResultDto>)session.getAttribute("resultList");
			}
			resultList.add(resultDto);
			
			//　問題を取得する
			ProblemDto problemDto = new ProblemDto();
			problemDto = problemList.get(problemNumber);

			//　結果Listをリクエストに保持する
			session.setAttribute("resultList", resultList);
			
			//　結果をリクエストに保持する
			request.setAttribute("resultDto", resultDto);
			
			//　問題番号をリクエストに保持する
			request.setAttribute("problemNumber", problemNumber);
			
			// 問題をリクエストスコープにセット
			request.setAttribute("problemDto", problemDto);
			
			// problem-userに遷移する
			request.getRequestDispatcher("WEB-INF/problem-user.jsp").forward(request, response);
		
		} catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
			response.sendRedirect("user-system-error.jsp");
		}
	}

}
