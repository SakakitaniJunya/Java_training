package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class ResetPasswordOperator
 */
@WebServlet(urlPatterns={"/reset-password-operator"},initParams={@WebInitParam(name="password", value="codetrain123")})
public class ResetPasswordOperator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		
		//値取得
		request.setCharacterEncoding("UTF-8");
		OperatorDto operatorDto = new OperatorDto();
		operatorDto.setOperatorId(Integer.parseInt(request.getParameter("operatorId")));
		operatorDto.setUpdateOperatorId(operator.getOperatorId());
		operatorDto.setPassword(getInitParameter("password"));
		
		//初期化処理
		try (Connection conn = DataSourceManager.getConnection()) {
			OperatorDao operatorDao = new OperatorDao(conn);
			operatorDao.resetPassword(operatorDto);
			
			//更新した旨のメッセージ（パスワードを初期化しました）をセッションスコープに保持する
			session.setAttribute("message", "パスワードを初期化しました");
			
			//ListUserServletにリダイレクト
			response.sendRedirect("operator-list");
		
		} catch (SQLException | NamingException e) {
			//エラーを画面に出力
			e.printStackTrace();
			//システムエラー画面に遷移
			response.sendRedirect("operator-system-error.jsp");
			
		}
	}
		

}
