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
		
		//�Z�b�V�����J�n
		HttpSession session = request.getSession(false);
		
		//�Z�b�V�����Ƀ��[�U��񂪂͂����Ă��Ȃ�������Alist-user
		CorporatorDto corporator = (CorporatorDto) session.getAttribute("corporator");
		if (session == null || session.getAttribute("corporator") == null) {
			// �g�b�v�y�[�W�Ɉړ�
			response.sendRedirect("login");
			return;
		}
		
		List<String> errorMessageList = new ArrayList<>();
		
			//���[�U�����擾
			request.setCharacterEncoding("UTF-8");
			UserDto dto = new UserDto();
			String email = request.getParameter("mail");
			if (!email.contains("@")) {
				errorMessageList.add("�s�����[���A�h���X�ł�");
			}
			dto.setEmail(request.getParameter("mail"));
			dto.setLastName(request.getParameter("lastName"));
			dto.setFirstName(request.getParameter("firstName"));
			dto.setUpdateCorporatorId(corporator.getCorporatorId());
			dto.setCorporationId(corporator.getCorporationId());
			dto.setPassword(getInitParameter("password"));
			
			//���[�U���̃G���[�\��
			
			errorMessageList.addAll(FieldValidator.userValidation(dto));
			
			// �G���[��������A��΂�
			if (errorMessageList.size() != 0) {
				session.setAttribute("errorMessageList", errorMessageList);
				response.sendRedirect("list-user");
				return;
			}

			//���[�U���o�^
			// �f�[�^�x�[�X�ɐڑ�
			try (Connection conn = DataSourceManager.getConnection()) {
				UserDao userDao = new UserDao(conn);
				
				//���[���A�h���X�������[���A�h���X�̏ꍇ
				List<UserDto> emailList = userDao.selectAll();
				for (UserDto userList: emailList) {
					if (userList.getEmail().equals(dto.getEmail())) {
						//list-user�ɑJ��
						response.sendRedirect("list-user");
						errorMessageList.add("���łɑ��݂��郁�[���A�h���X�ł�");	
						return;
					}
				}
				
				session.setAttribute("errorMassageList", errorMessageList);
				
				userDao.insertUser(dto);
				
				//�X�V���b�Z�[�W
				session.removeAttribute("message");
				session.setAttribute("message", "���p�҂�o�^���܂���");
				
				//list-user�ɑJ��
				response.sendRedirect("list-user");
				
				//request.getRequestDispatcher("list-user").forward(request, response);
				
			} catch (SQLException | NamingException e) {
				
				e.printStackTrace();
				response.sendRedirect("corporator-system-error.jsp");
			
			}
		
		}

}
