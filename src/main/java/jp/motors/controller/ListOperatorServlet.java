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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class ListOperatorServlet
 */
@WebServlet(name = "operator-list", urlPatterns = { "/operator-list" })
public class ListOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//セッション開始
		HttpSession session = request.getSession(false);
				
				
		//セッションがない場合、loginに遷移
		if (session == null || session.getAttribute("operator") == null) {
		
		//コース一覧ページに遷移する
		response.sendRedirect("login");
		return;
		}
	
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			//運用者情報全件取得
			List<OperatorDto> operatorList =  new ArrayList<>(); 
			OperatorDao operatorDao = new OperatorDao(conn);
			operatorList = operatorDao.selectAll();
			
			//初期化処理	
			List<OperatorDto> operatorList2 =  operatorDao.selectAll();
			List<Boolean> resetPasswordList = operatorDao.findResetPasswordById(operatorList2);
			request.setAttribute("resetPasswordList", resetPasswordList);
			
			int count = 0;
			for (OperatorDto operatordtos: operatorList) {
				operatordtos.setResetFlg(resetPasswordList.get(count));
				count++;
			}
			
			request.setAttribute("operatorList", operatorList);
			
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
			request.getRequestDispatcher("WEB-INF/list-operator.jsp").forward(request, response);
	
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
		doGet(request, response);
	}

}
