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
import jp.motors.dao.CorporatorDao;
import jp.motors.dto.CorporatorDto;

/**
 * Servlet implementation class ResetNewPasswordCorporator
 */
@WebServlet(name = "reset-new-password-corporator", urlPatterns = { "/reset-new-password-corporator" })
public class ResetNewPasswordCorporator extends HttpServlet {
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
			CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
								
					if (session == null || session.getAttribute("corporator") == null) {
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
					session.setAttribute("message", errorMessageList);
					response.sendRedirect("change-corporator-password");
					return;
					}
					
					// Corporatordtoに詰める
					CorporatorDto CorporatorDto = new CorporatorDto();
					CorporatorDto.setPassword(newPassword);
					int corporatorId = corporator.getCorporatorId();
					CorporatorDto.setCorporatorId(corporatorId);
					CorporatorDto.setUpdateNumber(corporator.getUpdateNumber());
					
					// SQL接続
					try (Connection conn = DataSourceManager.getConnection()){
						CorporatorDao corporatorDao = new CorporatorDao(conn);
						
						//パスワード更新
						corporatorDao.updatePassword(CorporatorDto);
						//更新メッセージセット
						session.setAttribute("navbarMessage", "パスワードを更新しました");
						
						//初期化フラグ削除
						session.removeAttribute("isChangeRequired");
						
						// ListUserServletにリダイレクトする
						response.sendRedirect("list-user");

					} catch (SQLException | NamingException e) {
						//例外メッセージ出力表示
						e.printStackTrace();
						//システムエラーページに遷移する
						response.sendRedirect("corporator-system-error.jsp");
					}	
					
			}


}
