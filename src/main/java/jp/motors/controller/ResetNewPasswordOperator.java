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
 * Servlet implementation class ResetNewPasswordOperator
 */
@WebServlet(name = "reset-new-password-operator", urlPatterns = { "/reset-new-password-operator" })
public class ResetNewPasswordOperator extends HttpServlet {
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
		//　セッションにログイン情報が入ってない場合、list-course.jspに遷移する
					HttpSession session = request.getSession(false);
					OperatorDto operator = (OperatorDto) session.getAttribute("operator");
										
							if (session == null || session.getAttribute("operator") == null) {
								response.sendRedirect("login");
								return;
							}
										
							//　フォームデータ取得
							request.setCharacterEncoding("UTF-8");
							String newPassword = request.getParameter("newPassword");
							String newPassword2 = request.getParameter("newPassword2");
					
							
							// 入力チェック,size（）でエラーメッセージを判別する
							List<String> errorMessageList = new ArrayList<>();
							errorMessageList = FieldValidator.passwordValidation(newPassword, newPassword2);
							if (errorMessageList.size() != 0) {
							session.setAttribute("navbarMessage", errorMessageList);
							response.sendRedirect("change-operator-password");
							return;
							}
							
							// Operatordtoに詰める
							OperatorDto OperatorDto = new OperatorDto();
							OperatorDto.setPassword(newPassword);
							int operatorId = operator.getOperatorId();
							OperatorDto.setOperatorId(operatorId);
							OperatorDto.setUpdateNumber(operator.getUpdateNumber());
							
							// SQL接続
							try (Connection conn = DataSourceManager.getConnection()){
								OperatorDao operatorDao = new OperatorDao(conn);
								
								//パスワード更新
								operatorDao.updatePassword(OperatorDto);
								//更新メッセージセット
								session.setAttribute("navbarMessage", "パスワードを更新しました");
								
								//初期化フラグ削除
								session.removeAttribute("isChangeRequired");
								
								// Operator-Messageにリダイレクトする
								response.sendRedirect("operator-message");

							} catch (SQLException | NamingException e) {
								//例外メッセージ出力表示
								e.printStackTrace();
								//システムエラーページに遷移する
								response.sendRedirect("operator-system-error.jsp");
							}	
							
					}

}
