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
import jp.motors.dao.CourseDao;
import jp.motors.dto.CategoryDto;
import jp.motors.dto.CourseDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/create-course")
public class CreateCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		// コネクションを取得する
		try (Connection conn = DataSourceManager.getConnection()) {
			// セッションを取得する
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("operator") == null) {
				// コース一覧に遷移する
				response.sendRedirect("login");
				return;
			}
		
			CategoryDao categoryDao = new CategoryDao(conn);
			List<CategoryDto> categoryList = categoryDao.selectAll();
		
			request.setAttribute("categoryList", categoryList);
		
			// エラーメッセージをリクエストに保持する
			request.setAttribute("MessageList", session.getAttribute("MessageList"));
			session.removeAttribute("MessageList");
						
			// カテゴリ作成画面に遷移する
			request.getRequestDispatcher("WEB-INF/create-course.jsp").forward(request, response);
		} catch (SQLException | NamingException e) {
			
			// 例外メッセージを出力表示
			e.printStackTrace();
						
			// システムエラー画面に遷移する
			response.sendRedirect("operator-system-error.jsp");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			// コース一覧に遷移する
			response.sendRedirect("login");
			return;
		}
		
		// セッションからOperator情報を取得する
		OperatorDto operator = (OperatorDto)session.getAttribute("operator");
		
		// フォームのデータを取得する
		request.setCharacterEncoding("UTF-8");
		CourseDto courseDto = new CourseDto();
		courseDto.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
		courseDto.setCourse(request.getParameter("course"));
		if (request.getParameter("estimatedStudyTime") != "") {
			courseDto.setEstimatedStudyTime(Integer.parseInt(request.getParameter("estimatedStudyTime")));
		} 
		courseDto.setCourseOverview(request.getParameter("courseOverview"));
		courseDto.setPrerequisite(request.getParameter("prerequisite"));
		courseDto.setGoal(request.getParameter("goal"));
		courseDto.setUpdateOperatorId(operator.getUpdateOperatorId());
		
		// 入力チェック
		List<String> errorMessageList = FieldValidator.courseValidation(courseDto);
		if (errorMessageList.size() != 0) {
			// チャンネル登録画面に遷移する
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("create-course");
			return;
		}
		
		try (Connection conn = DataSourceManager.getConnection()){
			// カテゴリを作成する
			CourseDao courseDao = new CourseDao(conn);
			courseDao.insert(courseDto);
			
			// カテゴリをリクエストスコープに保持する
			session.setAttribute("message", courseDto.getCourse() + "を登録しました");
			
			// コース一覧画面に遷移する
			response.sendRedirect("list-course-operator");
			
		} catch (SQLException | NamingException e) {
			
				// 例外メッセージを出力表示
				e.printStackTrace();
				
				// システムエラー画面に遷移する
				response.sendRedirect("operator-system-error.jsp");

		}		
		
		
		
	}

}
