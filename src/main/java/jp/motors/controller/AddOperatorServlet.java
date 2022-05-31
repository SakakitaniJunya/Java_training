package jp.motors.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import jp.motors.dao.OperatorDao;
import jp.motors.dto.OperatorDto;

/**
 * Servlet implementation class AddOperatorServlet
 */
@WebServlet(urlPatterns={"/add-operator"},initParams={@WebInitParam(name="password", value="codetrain123")})
public class AddOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ���O�C���y�[�W���ɑJ�ڂ���
				request.getRequestDispatcher("login").forward(request, response);
			
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
		
		List<String> errorMessageList = new ArrayList<>();
		
		// �l�擾
		OperatorDto operatorDto = new OperatorDto();
		request.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		operatorDto.setEmail(email);
		operatorDto.setFirstName(request.getParameter("firstName"));
		operatorDto.setLastName(request.getParameter("lastName"));
		int authority = Integer.parseInt(request.getParameter("authority"));
        operatorDto.setAuthority(authority);
        int opratorId = Integer.parseInt(request.getParameter("operatorId"));
		operatorDto.setOperatorId(opratorId);
		operatorDto.setUpdateOperatorId(operator.getOperatorId());
		operatorDto.setPassword(getInitParameter("password"));
		if (!email.contains("@")) {
			errorMessageList.add("�s�����[���A�h���X�ł�");
		}
		// ���̓`�F�b�N
		errorMessageList.addAll(FieldValidator.operatorValidation(operatorDto));
		//4. �G���[������ꍇ�A�G���[���b�Z�[�W���Z�b�V�����X�R�[�v�ɕێ����AListUserServlet�Ƀ��_�C���N�g����
		if (errorMessageList.size() != 0) {
			session.removeAttribute("errorMessageList");
			session.setAttribute("errorMessageList", errorMessageList);	
			session.setAttribute("operatorDto", operatorDto);
			response.sendRedirect("operator-list");
			return;
		}
		
		//���[���A�h���X�ɏd�����Ȃ����m�F
		List<OperatorDto> operatorList = new ArrayList<>();
		try (Connection conn = DataSourceManager.getConnection()) {
			OperatorDao operatordao = new OperatorDao(conn);
			operatorList = operatordao.selectAll();
			
			for(OperatorDto operatorLists: operatorList) {
				
				if ( email.equals(operatorLists.getEmail())) {
					session.setAttribute("errorMessageList", email + "�͂��łɑ��݂��܂�");
					response.sendRedirect("operator-list");
					return;
				}
			}
			
			
			//�G���[������ꍇ�A�G���[���b�Z�[�W���Z�b�V�����X�R�[�v�ɕێ����AListUserServlet�Ƀ��_�C���N�g����
			if (errorMessageList.size() != 0) {
				session.removeAttribute("errorMessageList");
				session.setAttribute("errorMessageList", errorMessageList);	
				response.sendRedirect("operator-list");
				return;
			}
		
			// �o�^

			operatordao.insert(operatorDto);
			session.setAttribute("message", "�^�p�҂�o�^���܂���");
			//operatorId���Z�b�g���Ă��邩�m�F
			
			//7. ListUserServlet�Ƀ��_�C���N�g
			response.sendRedirect("operator-list");
			
			
		} catch (SQLException | NamingException e) {
			//�G���[���o�͕\��
			e.printStackTrace();
			//�V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("operator-system-error.jsp");
			
		}
	}

}