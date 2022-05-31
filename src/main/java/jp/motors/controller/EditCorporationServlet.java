package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import jp.motors.dao.CorporationDao;
import jp.motors.dto.CorporationDto;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class EditCorporationServlet
 */
@WebServlet(name = "edit-corporation", urlPatterns = { "/edit-corporation" })
public class EditCorporationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログインページｌに遷移する
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
		
		//翌月取得
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currentDate.getTime());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		//Timestamp timestamp = new Timestamp(date.getTime());
		String displays = format.format( cal.getTime());
		//date = format.parse(displays);
		request.setAttribute("date",displays);
		//  Timestamp updatedTS = new Timestamp(displays);
			        
		//現在時刻の取得
		 Date dateObj = new Date();
		 SimpleDateFormat formats = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 // 日時情報を指定フォーマットの文字列で取得
		 String display = formats.format( dateObj );
		 request.setAttribute("dateObj", dateObj); 
		 
		 
		 
		 
		
		//会社情報の取得
		request.setCharacterEncoding("UTF-8");
		CorporationDto dto = new CorporationDto();
		dto.setCompanyName(request.getParameter("companyName"));
		dto.setDomain(request.getParameter("domain"));
		dto.setBillingAddress(request.getParameter("billingAddress"));
		int status = Integer.parseInt(request.getParameter("position"));
		dto.setUpdateOperatorId(operator.getOperatorId());
		dto.setCorporationId(Integer.parseInt(request.getParameter("corporationId")));
		
		//入力チェック
		List<String> errorMessageList = new ArrayList<>();
		errorMessageList = FieldValidator.corporationValidation(dto);
		
		
		
		//エラーがある場合list-corporationに遷移
		if (errorMessageList.size() != 0) {
			session.removeAttribute("errorMessageList");
			session.setAttribute("errorMessageList", errorMessageList);	
			response.sendRedirect("list-corporation");
			return;
		}
		
		//会社情報の登録
		try (Connection conn = DataSourceManager.getConnection()) {
			CorporationDao corporationDao = new CorporationDao(conn);
			
			if (status == 1) {
				//契約中から、解約予定
				dto.setFinishAt(Timestamp.valueOf(displays));
				corporationDao.updateCorporation(dto);
				
			} else if (status == 2) {
				
				//開始予定から解約（finishAtを今にする）現在時刻取得
				dto.setFinishAt(Timestamp.valueOf(display));
				corporationDao.updateCorporation(dto);
				
			} else {
			
				//その他、値の更新のみ
				dto.setFinishAt(null);
				corporationDao.updateCorporation(dto);
				
			}
			
			// チャンネル名をリクエストスコープに保持する
			session.setAttribute("message", "法人情報を更新しました");
			//list-corporationに遷移
			response.sendRedirect("list-corporation");
			return;
			
			} catch (SQLException | NamingException e) {
			//エラーを出力表示
			e.printStackTrace();
			//システムエラー画面に遷移する
			response.sendRedirect("operator-system-error.jsp");
			
		}
	}

}
