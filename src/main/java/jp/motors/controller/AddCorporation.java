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

import jp.motors.dto.CorporationDto;
import jp.motors.dto.OperatorDto;
import jp.motors.DataSourceManager;
import jp.motors.FieldValidator;
import jp.motors.dao.CorporationDao;

/**
 * Servlet implementation class AddCorporation
 */
@WebServlet(name = "add-corporation", urlPatterns = { "/add-corporation" })
public class AddCorporation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
				
		//�Z�b�V��������
		OperatorDto operator = (OperatorDto) session.getAttribute("operator");
		if (session == null || session.getAttribute("operator") == null) {
			// �g�b�v�y�[�W�Ɉړ�
			response.sendRedirect("login");
			return;
		}
		
		//�����擾
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
			        
		
		
		
		//��Џ��̎擾
		request.setCharacterEncoding("UTF-8");
		CorporationDto dto = new CorporationDto();
		dto.setCompanyName(request.getParameter("companyName"));
		dto.setDomain(request.getParameter("domain"));
		dto.setBillingAddress(request.getParameter("billingAddress"));
		dto.setUpdateOperatorId(operator.getOperatorId());
		dto.setStartAt(Timestamp.valueOf(displays));
		
		//���̓`�F�b�N
		List<String> errorMessageList = new ArrayList<>();
		errorMessageList = FieldValidator.corporationValidation(dto);
		
		
		
		//�G���[������ꍇlist-corporation�ɑJ��
		if (errorMessageList.size() != 0) {
			session.removeAttribute("errorMessageList");
			session.setAttribute("errorMessageList", errorMessageList);	
			response.sendRedirect("list-corporation");
			return;
		}
		
		//��Џ��̓o�^

		try (Connection conn = DataSourceManager.getConnection()) {
			CorporationDao corporationDao = new CorporationDao(conn);
			
			corporationDao.insert(dto);
			//�X�V���b�Z�[�W
			session.setAttribute("message", "�@�l��o�^���܂���");
		
			//list-corporation
			response.sendRedirect("list-corporation");
			
			
		} catch (SQLException | NamingException e) {
			//�G���[���o�͕\��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("operator-system-error.jsp");
			
		}
		
	}	

}
