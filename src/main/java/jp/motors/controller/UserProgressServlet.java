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


@WebServlet("/user-progress")
public class UserProgressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		try (Connection conn = DataSourceManager.getConnection()) {
			//
			HttpSession session = request.getSession(false);
			
			if (session == null || session.getAttribute("corporator") == null) {
				response.sendRedirect("login");
				return;
			}
			
			CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
			int corporationId = corporator.getCorporationId();
			
			ProgressDao progressDao = new ProgressDao(conn);
			
			List<ProgressDto> progressList = progressDao.selectByCorporationId(corporationId);
			
			for (ProgressDto dto : progressList) {
				SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
				 //åªç›éûçèéÊìæ
				 String display = format.format(dto.getLecturesFinishAt());
				 dto.setLecturesFinishTime(display);
			}
			
			request.setAttribute("progressList", progressList);
			
			request.setAttribute("uri", request.getRequestURI());
			
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			request.getRequestDispatcher("WEB-INF/user-progress.jsp").forward(request, response);
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			
			response.sendRedirect("corporator-system-error.jsp");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
