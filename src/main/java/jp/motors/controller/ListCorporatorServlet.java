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
import jp.motors.dao.CorporatorDao;
import jp.motors.dto.CorporatorDto;

/**
 * Servlet implementation class ListCorporatorServlet
 */
@WebServlet(name = "list-corporator", urlPatterns = { "/list-corporator" })
public class ListCorporatorServlet extends HttpServlet {
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
		
			
		//SQLから値取得
		try (Connection conn = DataSourceManager.getConnection()) {
				
			int corporationId = 0;
			//セッション処理
			if( session.getAttribute("corporationId") != null ) {
				corporationId = (int) session.getAttribute("corporationId");
			}
			if (corporationId == 0) {	
			// corporationIdを取得
				corporationId = Integer.parseInt(request.getParameter("corporationId"));
			
			}
				
				
				request.setAttribute("corporationId", corporationId);
				// corporationIdをセット
			
				// 法人カウント情報全件取得(ID)
				List<CorporatorDto> corporatorList =  new ArrayList<>(); 
				CorporatorDao corporatorDao = new CorporatorDao(conn);
				corporatorList = corporatorDao.selectByCorporationId(corporationId);
				
				
				//初期化処理	
				//List<CorporatorDto> corporatorList2 =  corporatorDao.selectAll();
				List<Boolean> resetPasswordList = corporatorDao.findResetPasswordById(corporatorList);
				request.setAttribute("resetPasswordList", resetPasswordList);
				
				int count = 0;
				for (CorporatorDto corporatordtos: corporatorList) {
					corporatordtos.setResetFlg(resetPasswordList.get(count));
					count++;
				}
				
				request.setAttribute("corporatorList", corporatorList);
				
				//リクエストスコープにセット
				request.setAttribute("corporatorList", corporatorList);
		
			
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
				request.getRequestDispatcher("WEB-INF/list-corporator.jsp").forward(request, response);
		
				
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
