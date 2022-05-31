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
import jp.motors.dao.IndexDao;
import jp.motors.dao.ProgressDao;
import jp.motors.dto.CourseDto;
import jp.motors.dto.IndexDto;
import jp.motors.dto.ProgressDto;
import jp.motors.dto.UserDto;

/**
 * Servlet implementation class CourseDetailsServlet
 */
@WebServlet("/course-details")
public class CourseDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// コースIDを取得していない場合
		if (request.getParameter("courseId") == null) {
			response.sendRedirect("list-course");
			return;
		}
		
		// セッションを取得する
		HttpSession session = request.getSession(true);
		
		// コースIDを取得する
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		//　コネクションプールを取得する
		try (Connection conn = DataSourceManager.getConnection()) {
			
			// 目次情報を取得する
			List<IndexDto> indexList = new ArrayList<>();
			IndexDao indexDao = new IndexDao(conn);
			indexList = indexDao.selectByCourseId(courseId);
			
			//　コースIDに該当するコース情報を取得する
			CourseDao courseDao = new CourseDao(conn);
			CourseDto courseDto = courseDao.selectByCourseId(courseId);
			
			// 一番目の目次IDを取得
			IndexDto firstIndexDto = indexList.get(0);
			
			//　ユーザ情報を取得する
			if (session.getAttribute("user") != null) {
				UserDto user = (UserDto)session.getAttribute("user");
				
				// 学習履歴を取得
				List<ProgressDto> progressList = new ArrayList<>();
				ProgressDao progressDao = new ProgressDao(conn);
				progressList = progressDao.selectByUserId(user.getUserId());
				
				// 学習済みの目次にデータ追加
				for (IndexDto index : indexList) {
					for(ProgressDto progress : progressList) {
						if (index.getIndexId() == progress.getIndexId()) {
							index.setLecturesFinished(true);
						}
					}
				}
				
				//　最新の学習目次のID取得
				int lastFinishIndexId = indexDao.selectLastFinishedIndexId(user.getUserId(), courseId);
				
				//　続きの目次IDを取得
				IndexDto nextIndexDto = new IndexDto();
				
				// はじめての場合
				if (lastFinishIndexId == 0) {
					nextIndexDto =indexList.get(0);
				} else {
					//　続きからの場合
					int count = 0;
					int indexCount = 0;
					for (IndexDto indexDto : indexList) {
						count++;
						if (indexDto.getIndexId() == lastFinishIndexId) {
							indexCount = count;
							break;
						}
					}
					
					if (indexCount == indexList.size()) {
						nextIndexDto = null;
					} else {
						nextIndexDto = indexList.get(indexCount);
					}
				}
				
				//　続きのindex情報をセッションに保持する
				request.setAttribute("nextIndexDto", nextIndexDto);
				
			} 
			
			//　コース情報と目次情報をリクエストに保持する
			request.setAttribute("courseDto", courseDto);
			request.setAttribute("indexList", indexList);
			request.setAttribute("firstIndexDto", firstIndexDto);
			
			//　URIをリクエストに保持する（ログイン時使用）
			request.setAttribute("uri", request.getRequestURI());
			
			//　セッションのメッセージをリクエストに保持し、セッションから削除
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			//　セッションのナブバーメッセージをリクエストに保持し、セッションから削除
			request.setAttribute("nabvarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			//　course-details.jspに伝送する
			request.getRequestDispatcher("WEB-INF/course-details.jsp").forward(request, response);
		
		} catch (SQLException | NamingException e) {
			// 例外メッセージを出力表示
			e.printStackTrace();
			
			//システムエラー画面に遷移する
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
