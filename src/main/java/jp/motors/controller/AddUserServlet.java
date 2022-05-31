package jp.motors.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.motors.DataSourceManager;
import jp.motors.FieldValidator;
import jp.motors.dto.UserDto;
import jp.motors.dao.UserDao;
import jp.motors.dto.CorporatorDto;


/**
 * Servlet implementation class AddUserServlet
 */
@WebServlet(urlPatterns={"/add-user"}, initParams={@WebInitParam(name="password", value="codetrain123")})
public class AddUserServlet extends HttpServlet {
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
		
		//セッションにユーザ情報がはいっていなかったら、list-user
		CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
		if (session == null || session.getAttribute("corporator") == null) {
			// トップページに移動
			response.sendRedirect("login");
			return;
		}
		
		List<String> errorMessageList = new ArrayList<>();
		
			//ユーザ情報を取得
			request.setCharacterEncoding("UTF-8");
			UserDto dto = new UserDto();
			String email = request.getParameter("mail");
			if (!email.contains("@")) {
				errorMessageList.add("不正メールアドレスです");
			}
			dto.setEmail(request.getParameter("mail"));
			dto.setLastName(request.getParameter("lastName"));
			dto.setFirstName(request.getParameter("firstName"));
			dto.setUpdateCorporatorId(corporator.getCorporatorId());
			dto.setCorporationId(corporator.getCorporationId());
			dto.setPassword(getInitParameter("password"));
			
			//ユーザ情報のエラー表示
			
			errorMessageList.addAll(FieldValidator.userValidation(dto));
			
			// エラーだったら、飛ばす
			if (errorMessageList.size() != 0) {
				session.setAttribute("errorMessageList", errorMessageList);
				response.sendRedirect("list-user");
				return;
			}

			//ユーザ情報登録
			// データベースに接続
			try (Connection conn = DataSourceManager.getConnection()) {
				UserDao userDao = new UserDao(conn);
				
				//メールアドレス同じメールアドレスの場合
				List<UserDto> emailList = userDao.selectAll();
				for (UserDto userList: emailList) {
					if (userList.getEmail().equals(dto.getEmail())) {
						//list-userに遷移
						response.sendRedirect("list-user");
						errorMessageList.add("すでに存在するメールアドレスです");	
						return;
					}
				}
				
				session.setAttribute("errorMassageList", errorMessageList);
				
				userDao.insertUser(dto);
				
				//更新メッセージ
				session.removeAttribute("message");
				session.setAttribute("message", "利用者を登録しました");
				
				//list-userに遷移
				response.sendRedirect("list-user");
				
				//request.getRequestDispatcher("list-user").forward(request, response);
				
			} catch (SQLException | NamingException e) {
				
				e.printStackTrace();
				response.sendRedirect("corporator-system-error.jsp");
			
			}
		
		}

}
