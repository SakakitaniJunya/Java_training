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

import jp.motors.FieldValidator;
import jp.motors.DataSourceManager;
import jp.motors.dao.CategoryDao;
import jp.motors.dto.CategoryDto;
import jp.motors.dto.OperatorDto;


@WebServlet("/edit-category")
public class EditCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("operator") == null) {
			// コース一覧に遷移する
			response.sendRedirect("login");
			return;
		}
		
		// セッションからOperator情報を取得する
		OperatorDto operator = (OperatorDto)session.getAttribute("operator");		
		
		// エラーメッセージをリクエストに保持する
		request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
		session.removeAttribute("errorMessageList");
		
		CategoryDto categoryDto = (CategoryDto) session.getAttribute("categoryDto");
		
		if (categoryDto != null) {
			request.setAttribute("categoryDto", categoryDto);
			session.removeAttribute("categoryDto");
		} else {
			request.setCharacterEncoding("UTF-8");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			
			try (Connection conn = DataSourceManager.getConnection()) {
				
				CategoryDao categoryDao = new CategoryDao(conn);
				
				categoryDto = categoryDao.selectByCategoryId(categoryId);
				
				request.setAttribute("categoryDto", categoryDto);
				
				if (operator.getAuthority() == 1) {
					response.sendRedirect("access-denied.jsp");
					return;
				}
				
			} catch (SQLException | NamingException e) {
				// 例外メッセージを出力表示
				e.printStackTrace();

				// システムエラー画面に遷移する
				response.sendRedirect("operator-system-error.jsp");
				return;
			}
			
			request.getRequestDispatcher("WEB-INF/edit-category.jsp").forward(request, response);
		}
		
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
		categoryDto.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
		categoryDto.setCategory(request.getParameter("category"));
		categoryDto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));
		
		// 入力チェック
		List<String> errorMessageList = FieldValidator.categoryValidation(categoryDto);
		if (errorMessageList.size() != 0) {
			// チャンネル編集画面に遷移する
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("edit-category?categoryId=" + request.getParameter("categoryId"));
			return;
		}
		
		// コネクションを取得する
		try (Connection conn = DataSourceManager.getConnection()){
			
			CategoryDao categoryDao = new CategoryDao(conn);
			
			if (operator.getAuthority() == 1) {
				response.sendRedirect("access-denied.jsp");
				return;
			}
			
			categoryDao.update(categoryDto);
			
			session.setAttribute("message", "カテゴリを更新しました");
			
			response.sendRedirect("list-course-operator");
			
			
		} catch (SQLException | NamingException e) {
			// カテゴリ名が重複している場合
			if (e.getMessage().contains("Duplicate entry")) {

				// エラーメッセージをリクエストスコープに保持する
				errorMessageList.add("カテゴリ「" + categoryDto.getCategory() + "」は既に存在します");
				session.setAttribute("errorMessageList", errorMessageList);

				// フォームのデータをリクエストスコープに保持する
				session.setAttribute("categoryDto", categoryDto);

				// カテゴリ編集画面に遷移する
				response.sendRedirect("edit-category?categoryId=" + request.getParameter("categoryId"));
			} else {
				// 例外メッセージを出力表示
				e.printStackTrace();
				
				// システムエラー画面に遷移する
				response.sendRedirect("operator-system-error.jsp");
			}
		
		}
		
	}

}
