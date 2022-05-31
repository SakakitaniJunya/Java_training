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
import jp.motors.dao.CourseDao;
import jp.motors.dao.IndexDao;
import jp.motors.dto.CourseDto;
import jp.motors.dto.IndexDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/create-text")
public class CreateTextServlet extends HttpServlet {
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
			
			// courseIdをリクエストスコープに保持する
			CourseDao courseDao = new CourseDao(conn);
			CourseDto courseDto = new CourseDto();
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			courseDto.setCourseId(courseId);
			courseDto = courseDao.selectByCourseId(courseId);	
			
			request.setAttribute("courseDto", courseDto);
			
			// エラーメッセージをリクエストに保持する
			request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
			session.removeAttribute("errorMessage");			
			
			request.getRequestDispatcher("WEB-INF/create-text.jsp").forward(request, response);
			
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
		IndexDto indexDto = new IndexDto();
		indexDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));
		indexDto.setIndexs(request.getParameter("indexs"));
		indexDto.setContent(request.getParameter("content"));
		indexDto.setUpdateOperatorId(operator.getUpdateOperatorId());
		
		// 入力チェック
		List<String> errorMessageList = FieldValidator.indexValidation(indexDto);
		if (errorMessageList.size() != 0) {
			// チャンネル登録画面に遷移する
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("create-text");
			return;
		}		
		
		try (Connection conn = DataSourceManager.getConnection()){
			IndexDao indexDao = new IndexDao(conn);
			
				// textを作成する
				indexDao.insert(indexDto);
				
				// textをリクエストスコープに保持する
				session.setAttribute("message", indexDto.getIndexs() + "を登録しました");
				
				// text一覧画面に遷移する
				response.sendRedirect("list-text?courseId=" + indexDto.getCourseId());
			
			
		} catch (SQLException | NamingException e) {
			
	
				// 例外メッセージを出力表示
				e.printStackTrace();
				
				// システムエラー画面に遷移する
				response.sendRedirect("operator-system-error.jsp");
		}
		
	}

}
