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


@WebServlet("/edit-course")
public class EditCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			// コース一覧に遷移する
			response.sendRedirect("login");
			return;
		}
		
		// セッションからOperator情報を取得する
		OperatorDto operator = (OperatorDto)session.getAttribute("operator");		
		
		// エラーメッセージをリクエストに保持する
		request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
		session.removeAttribute("errorMessageList");
		
		CourseDto courseDto = (CourseDto) session.getAttribute("courseDto");
		
		if (courseDto != null) {
			session.removeAttribute("courseDto");
		} else {
			request.setCharacterEncoding("UTF-8");
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			
			try (Connection conn = DataSourceManager.getConnection()){
				
				CourseDao courseDao = new CourseDao(conn);
				
				courseDto = courseDao.selectByCourseId(courseId);
				
				request.setAttribute("courseDto", courseDto);
				
				if (operator.getAuthority() == 1) {
					response.sendRedirect("access-denied.jsp");
					return;
				}	
				
			}catch (SQLException | NamingException e) {
				// 例外メッセージを出力表示
				e.printStackTrace();

				// システムエラー画面に遷移する
				response.sendRedirect("operator-system-error.jsp");
				return;
			}
		
		}
		
		try (Connection conn = DataSourceManager.getConnection()){
			
			// チャンネル情報を取得する
			CategoryDao categoryDao = new CategoryDao(conn);
			List<CategoryDto> categoryList = categoryDao.selectAll();
			
			request.setAttribute("categoryList", categoryList);
		
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			
			response.sendRedirect("operator-system-error.jsp");
			return;
		}
		
		request.getRequestDispatcher("WEB-INF/edit-course.jsp").forward(request, response);

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
		courseDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));
		courseDto.setCourse(request.getParameter("course"));
		courseDto.setEstimatedStudyTime(Integer.parseInt(request.getParameter("estimatedStudyTime")));
		courseDto.setCourseOverview(request.getParameter("courseOverview"));
		courseDto.setPrerequisite(request.getParameter("prerequisite"));
		courseDto.setGoal(request.getParameter("goal"));		
		courseDto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));
		
		// 入力チェック
		List<String> errorMessageList = FieldValidator.courseValidation(courseDto);
		if (errorMessageList.size() != 0) {
			// チャンネル登録画面に遷移する
			session.setAttribute("errorMessageList", errorMessageList);
			session.setAttribute("courseDto", courseDto);
			response.sendRedirect("edit-course?courseId=" + request.getParameter("courseId"));
			return;
		}		
		
		// コネクションを取得する
		try (Connection conn = DataSourceManager.getConnection()){
			
			CourseDao courseDao = new CourseDao(conn);
			
			if (operator.getAuthority() == 1) {
				response.sendRedirect("access-denied.jsp");
				return;
			}
			
			courseDao.update(courseDto);
			
			session.setAttribute("message", "コースを更新しました");
			
			response.sendRedirect("list-course-operator");
			
			
		} catch (SQLException | NamingException e) {
			// カテゴリ名が重複している場合
			if (e.getMessage().contains("Duplicate entry")) {

				// エラーメッセージをリクエストスコープに保持する
				errorMessageList.add("コース「" + courseDto.getCourse() + "」は既に存在します");
				session.setAttribute("errorMessageList", errorMessageList);

				// フォームのデータをリクエストスコープに保持する
				session.setAttribute("courseDto", courseDto);

				// カテゴリ編集画面に遷移する
				response.sendRedirect("edit-course?courseId=" + request.getParameter("courseId"));
			} else {
				// 例外メッセージを出力表示
				e.printStackTrace();
				
				// システムエラー画面に遷移する
				response.sendRedirect("operator-system-error.jsp");
			}
		
		}		
		
	}

}
