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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class DeleteOperatorServlet
 */
@WebServlet(name = "delete-operator", urlPatterns = { "/delete-operator" })
public class DeleteOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// loginにリダイレクトする
		response.sendRedirect("login");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//セッション開始
		HttpSession session = request.getSession(false);
				
		//セッション処理
		OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
			// トップページに移動
			response.sendRedirect("login");
			return;
		}
		
		//　フォームのデータ（運用者ID）を取得する
		request.setCharacterEncoding("UTF-8");
		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setOperatorId(Integer.parseInt(request.getParameter("operatorId")));
		operatorDto.setUpdateOperatorId(operator.getOperatorId());
		

		try (Connection conn = DataSourceManager.getConnection()) {

			//データベース接続
			OperatorDao operatorDao = new OperatorDao(conn);
				
			//削除
			operatorDao.deleteDelFlg(operatorDto);
			
			session.setAttribute("message", "運用者を削除しました");
			//7. ListUserServletにリダイレクト
			response.sendRedirect("operator-list");
			
			
		} catch (SQLException | NamingException e) {
			//エラーを出力表示
			e.printStackTrace();
			//システムエラー画面に遷移する
			response.sendRedirect("operator-system-error.jsp");
			
		}
		
	}

}
