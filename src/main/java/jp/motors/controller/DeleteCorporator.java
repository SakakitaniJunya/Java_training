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
import jp.motors.dao.CorporatorDao;
import jp.motors.dto.CorporatorDto;

/**
 * Servlet implementation class DeleteCorporator
 */
@WebServlet(name = "delete-corporator", urlPatterns = { "/delete-corporator" })
public class DeleteCorporator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログインページｌに遷移する
		response.sendRedirect("login");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//セッション開始
		HttpSession session = request.getSession(false);
								
		//セッション処理
		// OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
			// トップページに移動
			response.sendRedirect("login");
			return;
			}	
				
		//法人アカウント情報の取得
		request.setCharacterEncoding("UTF-8");
		CorporatorDto dto = new CorporatorDto();
		int corporationId = Integer.parseInt(request.getParameter("corporationId"));
		dto.setCorporationId(corporationId);
		int corporatorId = Integer.parseInt(request.getParameter("corporatorId"));
		dto.setCorporatorId(corporatorId);
		
		//sessionにcorporationIdを保持
		session.setAttribute("corporationId", corporationId);
		
		
		
		try (Connection conn = DataSourceManager.getConnection()) {
			CorporatorDao corporatorDao = new CorporatorDao(conn);
			
			//会社情報の削除
			corporatorDao.deleteById(corporatorId);
			//更新メッセージ
			session.setAttribute("message", "法人アカウントを削除しました");
				
			//list-corporation
			response.sendRedirect("list-corporator");
					
				
		} catch (SQLException | NamingException e) {
				//エラーを出力表示
				e.printStackTrace();
				//システムエラー画面に遷移する
				response.sendRedirect("operator-system-error.jsp");
				
		}
		
		
	}

}
