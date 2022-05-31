package jp.motors.controller;


import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
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
 * Servlet implementation class EditUserServlet
 */
@WebServlet(name = "edit-user", urlPatterns = { "/edit-user" })
public class EditUserServlet extends HttpServlet {
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
		CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
		if (session == null || session.getAttribute("corporator") == null) {
			// �g�b�v�y�[�W�Ɉړ�
			response.sendRedirect("login");
			return;
		}
		
		
		//�l�擾
		request.setCharacterEncoding("UTF-8");
		UserDto dto = new UserDto();
		dto.setEmail(request.getParameter("email"));
		dto.setLastName(request.getParameter("lastName"));
		dto.setFirstName(request.getParameter("firstName"));
		dto.setUpdateCorporatorId(corporator.getCorporatorId());
		dto.setCorporationId(corporator.getCorporationId());
		dto.setUserId(Integer.parseInt(request.getParameter("userId")));
		int activity = Integer.parseInt(request.getParameter("activity"));
		
		//���̓`�F�b�N
		List<String> errorMessageList = new ArrayList<>();
		errorMessageList = FieldValidator.userValidation(dto);
		
		// �G���[��������A��΂�
		if (errorMessageList.size() != 0) {
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("list-user");
			return;
		}

		
		
		//�f�[�^�x�[�X�o�^����
		try (Connection conn = DataSourceManager.getConnection()) {
			UserDao userDao = new UserDao(conn);
			

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
		        
			
			if (activity == 0) {
				//1. activity��0�̏ꍇ(����)
				dto.setDeleteAt(null);
				dto.setPauseAt(null);
				userDao.updateUser(dto);
				
				
			} else if (activity == 1) {
				//2. activity��1�̏ꍇ(�x�~)
				dto.setDeleteAt(null);
				dto.setPauseAt(null);
				userDao.updateUser(dto);
				
				
			} else if (activity == 2) {
				//3. activity��2�̏ꍇ(�x�~�\��)
				dto.setDeleteAt(null);
				dto.setPauseAt(Timestamp.valueOf(displays));
				userDao.updateUser(dto);
				
			} else { 
				//4. activity��3�̏ꍇ�i�폜�\��j
				dto.setDeleteAt(Timestamp.valueOf(displays));
				dto.setPauseAt(null);
				userDao.updateUser(dto);
				
				
			}
			//�G���[���b�Z�[�W��ێ�
			session.setAttribute("errorMessageList", errorMessageList);
			// �`�����l���������N�G�X�g�X�R�[�v�ɕێ�����
			session.setAttribute("message", "���[�U���X�V���܂���");
			//listuser�ɑJ��
			response.sendRedirect("list-user");
		
	
					
		} catch (SQLException | NamingException | IllegalArgumentException e) {
			
			//��O���b�Z�[�W���o��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("corporator-system-error.jsp");
		
		}
		
		
		
		
	}

}
