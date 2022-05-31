package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.ProgressDao;
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.ProgressDto;



@WebServlet("/search-user-progress")
public class SearchUserProgress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.sendRedirect("login");
	}
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//
		HttpSession session = request.getSession(false);
		
		//
		if (session == null || session.getAttribute("corporator") == null) {
			
			response.sendRedirect("login");
			return;
		}
		
			//
			request.setCharacterEncoding("UTF-8");
			String keyword = request.getParameter("keyword");
			
			//
			if(keyword.isEmpty()) {
				request.setAttribute("errorMassageList","検索キーワードを入力してください");
				response.sendRedirect("user-progress");
				return;
			}
			
			//
			try (Connection conn = DataSourceManager.getConnection()) {
				ProgressDao progressDao = new ProgressDao(conn);
				
				//
				String[] keywords = keyword.split(" ");
				CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
				int corporationId =  corporator.getCorporationId();
				List<ProgressDto> progressList = progressDao.selectByQueries(corporationId, keywords);
				
				//ハイライト用のキーワード
				for (ProgressDto dto : progressList) {
					SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
					 //現在時刻取得
					 String display = format.format(dto.getLecturesFinishAt());
					 dto.setLecturesFinishTime(display);
				}
				
				request.setAttribute("keyword", keyword);
				//
				request.setAttribute("progressList", progressList);
				request.getRequestDispatcher("WEB-INF/user-progress.jsp").forward(request, response);				

			} catch (SQLException | NamingException e) {
			
				e.printStackTrace();
								
			
				response.sendRedirect("corporator-system-error.jsp");
			}
	}
}
