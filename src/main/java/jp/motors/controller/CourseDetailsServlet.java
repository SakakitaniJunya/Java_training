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
import jp.motors.dao.CourseDao;
import jp.motors.dao.IndexDao;
import jp.motors.dao.ProgressDao;
import jp.motors.dto.CourseDto;
import jp.motors.dto.IndexDto;
import jp.motors.dto.ProgressDto;
import jp.motors.dto.UserDto;

/**
 * Servlet implementation class CourseDetailsServlet
 */
@WebServlet("/course-details")
public class CourseDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �R�[�XID���擾���Ă��Ȃ��ꍇ
		if (request.getParameter("courseId") == null) {
			response.sendRedirect("list-course");
			return;
		}
		
		// �Z�b�V�������擾����
		HttpSession session = request.getSession(true);
		
		// �R�[�XID���擾����
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		//�@�R�l�N�V�����v�[�����擾����
		try (Connection conn = DataSourceManager.getConnection()) {
			
			// �ڎ������擾����
			List<IndexDto> indexList = new ArrayList<>();
			IndexDao indexDao = new IndexDao(conn);
			indexList = indexDao.selectByCourseId(courseId);
			
			//�@�R�[�XID�ɊY������R�[�X�����擾����
			CourseDao courseDao = new CourseDao(conn);
			CourseDto courseDto = courseDao.selectByCourseId(courseId);
			
			// ��Ԗڂ̖ڎ�ID���擾
			IndexDto firstIndexDto = indexList.get(0);
			
			//�@���[�U�����擾����
			if (session.getAttribute("user") != null) {
				UserDto user = (UserDto)session.getAttribute("user");
				
				// �w�K�������擾
				List<ProgressDto> progressList = new ArrayList<>();
				ProgressDao progressDao = new ProgressDao(conn);
				progressList = progressDao.selectByUserId(user.getUserId());
				
				// �w�K�ς݂̖ڎ��Ƀf�[�^�ǉ�
				for (IndexDto index : indexList) {
					for(ProgressDto progress : progressList) {
						if (index.getIndexId() == progress.getIndexId()) {
							index.setLecturesFinished(true);
						}
					}
				}
				
				//�@�ŐV�̊w�K�ڎ���ID�擾
				int lastFinishIndexId = indexDao.selectLastFinishedIndexId(user.getUserId(), courseId);
				
				//�@�����̖ڎ�ID���擾
				IndexDto nextIndexDto = new IndexDto();
				
				// �͂��߂Ă̏ꍇ
				if (lastFinishIndexId == 0) {
					nextIndexDto =indexList.get(0);
				} else {
					//�@��������̏ꍇ
					int count = 0;
					int indexCount = 0;
					for (IndexDto indexDto : indexList) {
						count++;
						if (indexDto.getIndexId() == lastFinishIndexId) {
							indexCount = count;
							break;
						}
					}
					
					if (indexCount == indexList.size()) {
						nextIndexDto = null;
					} else {
						nextIndexDto = indexList.get(indexCount);
					}
				}
				
				//�@������index�����Z�b�V�����ɕێ�����
				request.setAttribute("nextIndexDto", nextIndexDto);
				
			} 
			
			//�@�R�[�X���Ɩڎ��������N�G�X�g�ɕێ�����
			request.setAttribute("courseDto", courseDto);
			request.setAttribute("indexList", indexList);
			request.setAttribute("firstIndexDto", firstIndexDto);
			
			//�@URI�����N�G�X�g�ɕێ�����i���O�C�����g�p�j
			request.setAttribute("uri", request.getRequestURI());
			
			//�@�Z�b�V�����̃��b�Z�[�W�����N�G�X�g�ɕێ����A�Z�b�V��������폜
			request.setAttribute("message", session.getAttribute("message"));
			session.removeAttribute("message");
			
			//�@�Z�b�V�����̃i�u�o�[���b�Z�[�W�����N�G�X�g�ɕێ����A�Z�b�V��������폜
			request.setAttribute("nabvarMessage", session.getAttribute("navbarMessage"));
			session.removeAttribute("navbarMessage");
			
			//�@course-details.jsp�ɓ`������
			request.getRequestDispatcher("WEB-INF/course-details.jsp").forward(request, response);
		
		} catch (SQLException | NamingException e) {
			// ��O���b�Z�[�W���o�͕\��
			e.printStackTrace();
			
			//�V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("user-system-error.jsp");

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
