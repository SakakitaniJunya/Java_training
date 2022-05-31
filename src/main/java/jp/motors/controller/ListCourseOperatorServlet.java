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
import jp.motors.dto.CourseDto;


@WebServlet("/list-course-operator")
public class ListCourseOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// コネクションを取得する
		try (Connection conn = DataSourceManager.getConnection()) {
			// セッションの取得する
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("operator") == null) {
				// コース一覧に遷移する
				response.sendRedirect("login");
				return;
			}		
			
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
			
			//　コース一覧データをリクエストに保持する
			session.setAttribute("courseList", courseList);
			
			//　URIをリクエストに保持する
			request.setAttribute("uri", request.getRequestURI());
			
			//　セッションのmessageをリクエストに保持する、セッションから削除する
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			//　セッションのmessageをリクエストに保持する、セッションから削除する
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessageList");
			
			//　セッションのnavbarmessageをリクエストに保持し、セッションから削除する
			request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			// コース一覧に遷移する
			request.getRequestDispatcher("WEB-INF/list-course-operator.jsp").forward(request, response);
		
		} catch (SQLException | NamingException e) {
			
			e.printStackTrace();
			
			response.sendRedirect("operator-system-error.jsp");
			
		}		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
