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
import jp.motors.dao.ClaimDao;
import jp.motors.dto.ClaimDto;

/**
 * Servlet implementation class ExpenseClaimServlet
 */
@WebServlet("/expense-claim")
public class ExpenseClaimServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// sessionを取得する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("corporator") == null) {
			response.sendRedirect("login");
			return;
		}
		
		//　法人IDを取得する
		int corporationId = Integer.parseInt(request.getParameter("corporationId"));
		
		//　データを取得する
		try (Connection conn = DataSourceManager.getConnection()) {
			ClaimDao claimDao = new ClaimDao(conn);
			
			List<ClaimDto> claimList = claimDao.selectAllByCorporationId(corporationId);
			
			request.setAttribute("claimList", claimList);
			
			request.getRequestDispatcher("WEB-INF/expense-claim.jsp").forward(request, response);
			
		} catch (SQLException | NamingException e) {
			//例外メッセージを出力
			e.printStackTrace();
			//システムエラー画面に遷移する
			response.sendRedirect("corporator-system-error.jsp");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
