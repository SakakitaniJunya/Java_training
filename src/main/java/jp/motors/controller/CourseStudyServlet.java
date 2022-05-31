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
 * Servlet implementation class StudyServlet
 */
@WebServlet("/course-study")
public class CourseStudyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �ڎ�ID���擾�ł��Ȃ��ꍇ
		if (request.getParameter("indexId") == null) {
			response.sendRedirect("course-details");
			return;
		}
		
		//�@�Z�b�V�������擾����
		HttpSession session = request.getSession(true);
		
		//�@�ڎ�ID���擾����
		int indexId = Integer.parseInt(request.getParameter("indexId"));
		
		
		//�@�R�l�N�V�����v�[�����擾����
		try (Connection conn = DataSourceManager.getConnection()) {
		
			// �ڎ�ID�ɊY������e�L�X�g���擾����
			IndexDao indexDao = new IndexDao(conn);
			IndexDto indexDto = indexDao.selectByIndexId(indexId);
			
			//�@�R�[�XID�ɊY������R�[�X�����擾����
			CourseDao courseDao = new CourseDao(conn);
			CourseDto courseDto = courseDao.selectByCourseId(indexDto.getCourseId());
			
			//�@Free�R�[�X�t���O���擾����
			Boolean freeCourseFlg = indexDao.selectFreeCourseFlgByIndexId(indexId);
			
			//�@���[�U��񂪓����ĂȂ�����free�R�[�X����Ȃ��ꍇ�̓R�[�X�ꗗ�Ƀ��_�C���N�g����
			if (session.getAttribute("user") == null && !freeCourseFlg) {
				response.sendRedirect("list-course");
				return;
			}

			// �ڎ������擾����
			List<IndexDto> indexList = new ArrayList<>();
			indexList = indexDao.selectByCourseId(indexDto.getCourseId());
			
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
				
				//�@���ւ̖ڎ�ID���擾
				int count = 0;
				int indexCount = 0;
				for (IndexDto dto : indexList) {
					count++;
					if (dto.getIndexId() == indexId) {
						indexCount = count;
						break;
					}
				}
				
				IndexDto nextIndexDto = new IndexDto();
				if (indexCount == indexList.size()) {
					nextIndexDto = null;
				} else {
					nextIndexDto = indexList.get(indexCount);
				}
				request.setAttribute("nextIndexDto", nextIndexDto);
			}
			
			
			//�@�e�L�X�g���Z�b�V�����ɕێ�����
			request.setAttribute("courseDto", courseDto);
			request.setAttribute("indexDto", indexDto);
			request.setAttribute("indexList", indexList);
			
			//�@URI�����N�G�X�g�ɕێ�����
			request.setAttribute("uri", request.getRequestURI());
			
			//�@study�ɓ]������
			request.getRequestDispatcher("WEB-INF/course-study.jsp").forward(request, response);
			
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
		
		// ���[�U�������ĂȂ��ꍇ�Aindex.jsp�ɑJ�ڂ���
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null || request.getParameter("indexId") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		// ���[�U�����擾����
		UserDto user = (UserDto)session.getAttribute("user");
		
		//�@nextIndexId��indexId���擾����
		int indexId = Integer.parseInt(request.getParameter("indexId"));
		int nextIndexId = 0;
		if (request.getParameter("nextIndexId") != null) {
			nextIndexId = Integer.parseInt(request.getParameter("nextIndexId"));
		}
		// �f�[�^���t�H�[������擾����
		request.setCharacterEncoding("UTF-8");
		ProgressDto progressDto = new ProgressDto();
		progressDto.setUserId(user.getUserId());
		progressDto.setIndexId(indexId);
		
		//�@����DB�o�^����
		try (Connection conn = DataSourceManager.getConnection()) {
			ProgressDao progressDao = new ProgressDao(conn);
			progressDao.insert(progressDto);
			
			// �ڎ�ID�ɊY������e�L�X�g���擾����
			//IndexDao indexDao = new IndexDao(conn);
			//IndexDto indexDto = indexDao.selectByIndexId(indexId);
			
			// �ڎ������擾����
			//List<IndexDto> indexList = new ArrayList<>();
			//indexList = indexDao.selectByCourseId(indexDto.getCourseId());
			
			// ��ԍŌ�̖ڎ�������A���X�g�R�[�X�Ƀ��_�C���N�g����
			if (nextIndexId == 0) {
				response.sendRedirect("list-course");
				return;
			}

			//�@study�ɑJ�ڂ���
			response.sendRedirect("course-study?indexId=" + nextIndexId);
			
		} catch (SQLException | NamingException e) {
			// ��O���b�Z�[�W���o�͕\��
			e.printStackTrace();
			
			//�V�X�e���G���[��ʂɑJ�ڂ���
			response.sendRedirect("user-system-error.jsp");
		} 
		
	}

}
