package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.CourseDao;
import jp.motors.dao.IndexDao;
import jp.motors.dto.CourseDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/delete-course")
public class DeleteCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login");
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
		courseDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));
		courseDto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));
		
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			try {
				
				// トランザクションを開始する
				conn.setAutoCommit(false);

				CourseDao courseDao = new CourseDao(conn);
				
				if (operator.getAuthority() == 1) {
					response.sendRedirect("access-denied.jsp");
					return;
				}
				
				// コース情報を削除する
				courseDao.deleteByCourseId(courseDto);
				
				// コースに紐づくテキストを削除する
				IndexDao indexDao =new IndexDao(conn);
				indexDao.deleteByCourseId(courseDto);
				
				
				// コミットする
				conn.commit();
				
				session.setAttribute("message", "コースを削除しました");
				
			} catch (SQLException e) {
			
				// ロールバックする
				conn.rollback();
				throw e;
		
			} finally {
				conn.setAutoCommit(true);
			}
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
