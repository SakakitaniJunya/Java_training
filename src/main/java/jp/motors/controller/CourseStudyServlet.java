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
 * Servlet implementation class StudyServlet
 */
@WebServlet("/course-study")
public class CourseStudyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 目次IDを取得できない場合
		if (request.getParameter("indexId") == null) {
			response.sendRedirect("course-details");
			return;
		}
		
		//　セッションを取得する
		HttpSession session = request.getSession(true);
		
		//　目次IDを取得する
		int indexId = Integer.parseInt(request.getParameter("indexId"));
		
		
		//　コネクションプールを取得する
		try (Connection conn = DataSourceManager.getConnection()) {
		
			// 目次IDに該当するテキストを取得する
			IndexDao indexDao = new IndexDao(conn);
			IndexDto indexDto = indexDao.selectByIndexId(indexId);
			
			//　コースIDに該当するコース情報を取得する
			CourseDao courseDao = new CourseDao(conn);
			CourseDto courseDto = courseDao.selectByCourseId(indexDto.getCourseId());
			
			//　Freeコースフラグを取得する
			Boolean freeCourseFlg = indexDao.selectFreeCourseFlgByIndexId(indexId);
			
			//　ユーザ情報が入ってないかつfreeコースじゃない場合はコース一覧にリダイレクトする
			if (session.getAttribute("user") == null && !freeCourseFlg) {
				response.sendRedirect("list-course");
				return;
			}

			// 目次情報を取得する
			List<IndexDto> indexList = new ArrayList<>();
			indexList = indexDao.selectByCourseId(indexDto.getCourseId());
			
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
				
				//　次への目次IDを取得
				int count = 0;
				int indexCount = 0;
				for (IndexDto dto : indexList) {
					count++;
					if (dto.getIndexId() == indexId) {
						indexCount = count;
						break;
					}
				}
				
				IndexDto nextIndexDto = new IndexDto();
				if (indexCount == indexList.size()) {
					nextIndexDto = null;
				} else {
					nextIndexDto = indexList.get(indexCount);
				}
				request.setAttribute("nextIndexDto", nextIndexDto);
			}
			
			
			//　テキストをセッションに保持する
			request.setAttribute("courseDto", courseDto);
			request.setAttribute("indexDto", indexDto);
			request.setAttribute("indexList", indexList);
			
			//　URIをリクエストに保持する
			request.setAttribute("uri", request.getRequestURI());
			
			//　studyに転送する
			request.getRequestDispatcher("WEB-INF/course-study.jsp").forward(request, response);
			
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
		
		// ユーザ情報入ってない場合、index.jspに遷移する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null || request.getParameter("indexId") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		// ユーザ情報を取得する
		UserDto user = (UserDto)session.getAttribute("user");
		
		//　nextIndexIdとindexIdを取得する
		int indexId = Integer.parseInt(request.getParameter("indexId"));
		int nextIndexId = 0;
		if (request.getParameter("nextIndexId") != null) {
			nextIndexId = Integer.parseInt(request.getParameter("nextIndexId"));
		}
		// データをフォームから取得する
		request.setCharacterEncoding("UTF-8");
		ProgressDto progressDto = new ProgressDto();
		progressDto.setUserId(user.getUserId());
		progressDto.setIndexId(indexId);
		
		//　情報をDB登録する
		try (Connection conn = DataSourceManager.getConnection()) {
			ProgressDao progressDao = new ProgressDao(conn);
			progressDao.insert(progressDto);
			
			// 目次IDに該当するテキストを取得する
			//IndexDao indexDao = new IndexDao(conn);
			//IndexDto indexDto = indexDao.selectByIndexId(indexId);
			
			// 目次情報を取得する
			//List<IndexDto> indexList = new ArrayList<>();
			//indexList = indexDao.selectByCourseId(indexDto.getCourseId());
			
			// 一番最後の目次完了後、リストコースにリダイレクトする
			if (nextIndexId == 0) {
				response.sendRedirect("list-course");
				return;
			}

			//　studyに遷移する
			response.sendRedirect("course-study?indexId=" + nextIndexId);
			
		} catch (SQLException | NamingException e) {
			// 例外メッセージを出力表示
			e.printStackTrace();
			
			//システムエラー画面に遷移する
			response.sendRedirect("user-system-error.jsp");
		} 
		
	}

}
