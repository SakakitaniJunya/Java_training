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
import jp.motors.FieldValidator;
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class ChangeOperatorPassword
 */
@WebServlet("/change-operator-password")
public class ChangeOperatorPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// セッションにログイン情報が入ってない場合、list-course.jspに遷移する
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("operator") == null) {
			response.sendRedirect("login");
			return;
		}
				
		//　セッション内のエラーメッセージをリクエストスコープに保持し、セッション内から削除
		request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
		session.removeAttribute("errorMessageList");
				
		 		
		request.getRequestDispatcher("WEB-INF/change-password-operator.jsp").forward(request, response);
		
		

	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//　セッションにログイン情報が入ってない場合、login.jspに遷移する
		HttpSession session = request.getSession(false);
		OperatorDto operator = (OperatorDto) session.getAttribute("operator");
				
		if (session == null || session.getAttribute("operator") == null) {
			response.sendRedirect("login");
			return;
		}
				
		//　フォームデータ取得
		request.setCharacterEncoding("UTF-8");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String newPassword2 = request.getParameter("newPassword2");
				
		// 入力チェック,size（）でエラーメッセージを判別する
		List<String> errorMessageList = new ArrayList<>();
		errorMessageList = FieldValidator.passwordValidation(newPassword, newPassword2);
		if (errorMessageList.size() != 0) {
			session.setAttribute("message", errorMessageList);
			response.sendRedirect("change-operator-password");
			return;
		}
		
		// userdtoに詰める
		OperatorDto OperatorDto = new OperatorDto();
		OperatorDto.setPassword(newPassword);
		int operatorId = operator.getOperatorId();
		OperatorDto.setOperatorId(operatorId);
		OperatorDto.setUpdateNumber(operator.getUpdateNumber());
		
		try (Connection conn = DataSourceManager.getConnection()){
			OperatorDao operatorDao = new OperatorDao(conn);
			
			//現在のパスワード入力確認
			OperatorDto operatorDto2 = new OperatorDto();
			operatorDto2 = operatorDao.findPasswordByuserIdPassword(operatorId, oldPassword);
			
			
			// ログイン失敗時
			if (operatorDto2 == null) {
				session.setAttribute("navbarMessage", "現在のパスワードが間違っています");
				// ListUserServletにリダイレクトする
				response.sendRedirect("operator-message");
				return;

			}
			
 			//パスワード更新
			//int count = 
			operatorDao.updatePassword(OperatorDto);
			//更新メッセージセット
			session.setAttribute("navbarMessage", "パスワードを更新しました");
			// ListUserServletにリダイレクトする
			response.sendRedirect("operator-message");

		} catch (SQLException | NamingException e) {
			//例外メッセージ出力表示
			e.printStackTrace();
			//システムエラーページに遷移する
			response.sendRedirect("operator-system-error.jsp");
		}
	}

}
