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
		// コネクションを取得する
		try (Connection conn = DataSourceManager.getConnection()) {
			// セッションの取得する
			HttpSession session = request.getSession(true);
			
			// セッションからリザルトを削除する
			session.removeAttribute("resultList");
			
			List<CourseDto> courseList = new ArrayList<>();
			if (request.getParameter("categoryId") == null) {
	
				//　コース一覧を取得する
				CourseDao courseDao = new CourseDao(conn);
				courseList = courseDao.selectAll();
				
			} else {
				
				// カテゴリIDを取得する
				int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			
				//　コース一覧を取得する
				CourseDao courseDao = new CourseDao(conn);
				courseList = courseDao.selectByCategoryId(categoryId);
			}
			
			if (session.getAttribute("user") != null) {
				ResultDao resultDao = new ResultDao(conn);
				ProblemDao problemDao = new ProblemDao(conn);
				
				// ユーザを取得する
				UserDto user = (UserDto)session.getAttribute("user");
				
				for (CourseDto courseDto : courseList) {
					//　結果を取得する
					List<ResultDto> resultList = resultDao.selectByUserIdAndCourseId(user.getUserId(), courseDto.getCourseId());
					
					int score = 0;
					//　点数を計算する
						for (ResultDto resultDto : resultList){
							if (problemDao.selectCorrectSelectionByProblemId(resultDto.getProblemId()) == resultDto.getSelectedSelectionId()) {
								score += 10;
							}
						}
					courseDto.setScore(score);
				}
			}
			
			
			//　コース一覧データをリクエストに保持する
			request.setAttribute("courseList", courseList);
			
			//　URIをリクエストに保持する
			request.setAttribute("uri", request.getRequestURI());
			
			//　セッションのmessageをリクエストに保持する、セッションから削除する
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessageList");
			
			//　セッションのmessageをリクエストに保持する、セッションから削除する
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			//　セッションのnavbarmessageをリクエストに保持し、セッションから削除する
			request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			// コース一覧に遷移する
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
