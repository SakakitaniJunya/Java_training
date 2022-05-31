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
import jp.motors.dao.IndexDao;
import jp.motors.dto.IndexDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/delete-text")
public class DeleteTextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// コース一覧に遷移
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
		IndexDto indexDto = new IndexDto();
		indexDto.setIndexId(Integer.parseInt(request.getParameter("indexId")));	
		indexDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));	
		indexDto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));		
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			try {
				
				// トランザクションを開始する
				conn.setAutoCommit(false);

				IndexDao indexDao = new IndexDao(conn);
				
				if (operator.getAuthority() == 1) {
					response.sendRedirect("access-denied.jsp");
					return;
				}
				
				// テキスト情報を削除する
				indexDao.deleteByIndexId(indexDto);
				
				// コミットする
				conn.commit();
				
				session.setAttribute("message", "テキストを削除しました");
				
			} catch (SQLException e) {
			
				// ロールバックする
				conn.rollback();
				throw e;
		
			} finally {
				conn.setAutoCommit(true);
			}
			// テキスト一覧画面に遷移する
			response.sendRedirect("list-text?courseId=" + indexDto.getCourseId());
			
		} catch (SQLException | NamingException e) {
			
			// 例外メッセージを出力表示
			e.printStackTrace();
			
			// システムエラー画面に遷移する
			response.sendRedirect("operator-system-error.jsp");
		}
	}		

}
