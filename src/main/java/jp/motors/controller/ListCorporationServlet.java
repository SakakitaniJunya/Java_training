package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.CorporationDao;
import jp.motors.dto.CorporationDto;

/**
 * Servlet implementation class ListCorporationServlet
 */
@WebServlet(name = "list-corporation", urlPatterns = { "/list-corporation" })
public class ListCorporationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			//セッション開始
			HttpSession session = request.getSession(false);
					
			//セッション処理
			//OperatorDto operator = (OperatorDto) session.getAttribute("operator");
			if (session == null || session.getAttribute("operator") == null) {
				// トップページに移動
				response.sendRedirect("login");
				return;
			}
			
			//　法人IDが入っていたらセッションから削除
			if( session.getAttribute("corporationId") != null ) {
				session.removeAttribute("corporationId");
			}
			
			//現在時刻取得
			 Date dateObj = new Date();
			 //SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
			 // 日時情報を指定フォーマットの文字列で取得
			 //String display = format.format( dateObj );
			 request.setAttribute("dateObj", dateObj); 
			 
			 
			
			//SQLから値取得
		try (Connection conn = DataSourceManager.getConnection()) {
				
				//運用者情報全件取得
				List<CorporationDto> corporationList =  new ArrayList<>(); 
				CorporationDao corporationDao = new CorporationDao(conn);
				corporationList = corporationDao.selectAll();
			
			    //リクエストスコープにセット
				request.setAttribute("corporationList", corporationList);
				
				//セッションのmessageをリクエストに保持する、セッションから削除する
				request.setAttribute("message", session.getAttribute("message"));
				session.removeAttribute("message");
							
				// ナブバーメッセージをリクエストに保持する
				request.setAttribute("navbarMessage", session.getAttribute("navbarMessage"));
				session.removeAttribute("navbarMessage");
				
				//エラーメッセージをクエストに保持する、セッションから削除する
				request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
				session.removeAttribute("errorMessageList");
				
				//list-userに遷移する
				request.getRequestDispatcher("WEB-INF/list-corporation.jsp").forward(request, response);
		
				
			//list-corporator.jspに遷移
		} catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
		response.sendRedirect("operator-system-error.jsp");
			
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
