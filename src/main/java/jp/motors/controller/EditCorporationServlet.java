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
		// ���O�C���y�[�W���ɑJ�ڂ���
		response.sendRedirect("login");
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
			        
		//���ݎ����̎擾
		 Date dateObj = new Date();
		 SimpleDateFormat formats = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 // ���������w��t�H�[�}�b�g�̕�����Ŏ擾
		 String display = formats.format( dateObj );
		 request.setAttribute("dateObj", dateObj); 
		 
		 
		 
		 
		
		//��Џ��̎擾
		request.setCharacterEncoding("UTF-8");
		CorporationDto dto = new CorporationDto();
		dto.setCompanyName(request.getParameter("companyName"));
		dto.setDomain(request.getParameter("domain"));
		dto.setBillingAddress(request.getParameter("billingAddress"));
		int status = Integer.parseInt(request.getParameter("position"));
		dto.setUpdateOperatorId(operator.getOperatorId());
		dto.setCorporationId(Integer.parseInt(request.getParameter("corporationId")));
		
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
			
			if (status == 1) {
				//�_�񒆂���A���\��
				dto.setFinishAt(Timestamp.valueOf(displays));
				corporationDao.updateCorporation(dto);
				
			} else if (status == 2) {
				
				//�J�n�\�肩����ifinishAt�����ɂ���j���ݎ����擾
				dto.setFinishAt(Timestamp.valueOf(display));
				corporationDao.updateCorporation(dto);
				
			} else {
			
				//���̑��A�l�̍X�V�̂�
				dto.setFinishAt(null);
				corporationDao.updateCorporation(dto);
				
			}
			
			// �`�����l���������N�G�X�g�X�R�[�v�ɕێ�����
			session.setAttribute("message", "�@�l�����X�V���܂���");
			//list-corporation�ɑJ��
			response.sendRedirect("list-corporation");
			return;
			
			} catch (SQLException | NamingException e) {
			//�G���[���o�͕\��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("operator-system-error.jsp");
			
		}
	}

}
