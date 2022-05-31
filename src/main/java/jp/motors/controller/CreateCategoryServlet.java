package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
import jp.motors.dao.CategoryDao;
import jp.motors.dto.CategoryDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/create-category")
public class CreateCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			// コース一覧に遷移する
			response.sendRedirect("login");
			return;
		}
		
		// エラーメッセージをリクエストに保持する
		request.setAttribute("MessageList", session.getAttribute("MessageList"));
		session.removeAttribute("MessageList");
				
		// カテゴリ作成画面に遷移する
		request.getRequestDispatcher("WEB-INF/create-category.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			// コース一覧に遷移する
			response.sendRedirect("login");
			return;
		}
		
		// セッションからOperator情報を取得する
		OperatorDto operator = (OperatorDto)session.getAttribute("operator");
		
		// フォームのデータを取得する
		request.setCharacterEncoding("UTF-8");
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategory(request.getParameter("category"));
		categoryDto.setUpdateOperatorId(operator.getUpdateOperatorId());
		
		// 入力チェック
		List<String> errorMessageList = FieldValidator.categoryValidation(categoryDto);
		if (errorMessageList.size() != 0) {
			// チャンネル登録画面に遷移する
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("create-category");
			return;
		}
		
		try (Connection conn = DataSourceManager.getConnection()){
			CategoryDao categoryDao = new CategoryDao(conn);
			
			CategoryDto checkCategoryDto = categoryDao.checkCategory("category");
			
			if (checkCategoryDto.getCategory() != null) {
				// エラーメッセージをセッションスコープに保持する
				errorMessageList.add("カテゴリ「" + categoryDto.getCategory() + "」は既に存在します");
				session.setAttribute("errorMessageList", errorMessageList);
				
				// フォームのデータをセッションスコープに保持する
				session.setAttribute("categoryDto", categoryDto);

				// カテゴリ作成画面に遷移する
				response.sendRedirect("create-category");
				return;
			} else {
				// カテゴリを作成する
				categoryDao.insert(categoryDto);
				
				// カテゴリをリクエストスコープに保持する
				session.setAttribute("message", categoryDto.getCategory() + "を登録しました");
				
				// コース一覧画面に遷移する
				response.sendRedirect("list-course-operator");
			}
			
		} catch (SQLException | NamingException e) {
			
			// カテゴリ名が重複しているとき
			if (e.getMessage().contains("Duplicate entry")) {
	
				// 例外メッセージを出力表示
				e.printStackTrace();
				
				// システムエラー画面に遷移する
				response.sendRedirect("operator-system-error.jsp");
			}

		}
		
	}

}
