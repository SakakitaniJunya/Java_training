package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.CourseDto;
import jp.motors.dto.IndexDto;

public class CourseDao {

	/** �R�l�N�V���� */
	protected Connection conn;

	/**
	 * �R���X�g���N�^
	 * �R�l�N�V�������t�B�[���h�ɐݒ肷��
	 * @param conn �R�l�N�V����
	 */
	public CourseDao(Connection conn) {
        this.conn = conn;
    }
	
	/**
	 * �R�[�X�����擾����
	 * @return �R�[�X��񃊃X�g
	 * @throws SQLException SQL��O
	 */
	public List<CourseDto> selectAll() throws SQLException {

		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("         CATEGORY");
		sb.append("        ,COURSE.CATEGORY_ID");
		sb.append("        ,COURSE");
		sb.append("        ,COURSE_ID");
		sb.append("        ,COURSE_OVERVIEW");
		sb.append("        ,PREREQUISITE");
		sb.append("        ,GOAL");
		sb.append("        ,ESTIMATED_STUDY_TIME");
		sb.append("        ,COURSE.UPDATE_OPERATOR_ID");
		sb.append("        ,COURSE.UPDATE_AT");
		sb.append("        ,COURSE.UPDATE_NUMBER");
		sb.append("        ,FREE_COURSE_FLG");
		sb.append("        ,DELETE_FLG");
		sb.append("    FROM");
		sb.append("         COURSE INNER JOIN CATEGORY");
		sb.append("      ON COURSE.CATEGORY_ID = CATEGORY.CATEGORY_ID");
		sb.append("   WHERE DELETE_FLG = 0");
		sb.append("   ORDER BY");
		sb.append("         COURSE.CATEGORY_ID, COURSE_ID");


		List<CourseDto> list = new ArrayList<>();
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
		try (Statement stmt = conn.createStatement()) {
			
			// SQL�������s����
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				CourseDto dto = new CourseDto();
				dto.setCategory(rs.getString("CATEGORY"));
				dto.setCategoryId(rs.getInt("COURSE.CATEGORY_ID"));
				dto.setCourse(rs.getString("COURSE"));
				dto.setCourseId(rs.getInt("COURSE_ID"));
				dto.setCourseOverview(rs.getString("COURSE_OVERVIEW"));
				dto.setPrerequisite(rs.getString("PREREQUISITE"));
				dto.setGoal(rs.getString("GOAL"));
				dto.setEstimatedStudyTime(rs.getInt("ESTIMATED_STUDY_TIME"));
				dto.setUpdateOperatorId(rs.getInt("COURSE.UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("COURSE.UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("COURSE.UPDATE_NUMBER"));
				dto.setIsFreeCourseFlg(rs.getBoolean("FREE_COURSE_FLG"));
				dto.setDeleteFlg(rs.getBoolean("DELETE_FLG"));
				
				//�@�R�[�X���Ƃ̖ڎ��ꗗ���擾����
				IndexDao indexDao = new IndexDao(conn);
				
				List<IndexDto> indexList = indexDao.selectByCourseId(rs.getInt("COURSE_ID"));
				StringBuffer indexs = new StringBuffer();
				for (IndexDto index : indexList) {
					
					
					indexs.append(index.getIndexs());
					indexs.append(" ");
				}
				dto.setIndexs(indexs.toString());
				
				list.add(dto);
				
			}
			return list;
		}
	}
	
	/**
	 * �R�[�X�����擾����
	 * @return �R�[�X��񃊃X�g
	 * @throws SQLException SQL��O
	 */
	public List<CourseDto> selectByCategoryId(int categoryId) throws SQLException {

		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("         CATEGORY");
		sb.append("        ,COURSE.CATEGORY_ID");
		sb.append("        ,COURSE");
		sb.append("        ,COURSE_ID");
		sb.append("        ,COURSE_OVERVIEW");
		sb.append("        ,PREREQUISITE");
		sb.append("        ,GOAL");
		sb.append("        ,ESTIMATED_STUDY_TIME");
		sb.append("        ,COURSE.UPDATE_OPERATOR_ID");
		sb.append("        ,COURSE.UPDATE_AT");
		sb.append("        ,COURSE.UPDATE_NUMBER");
		sb.append("        ,FREE_COURSE_FLG");
		sb.append("        ,DELETE_FLG");
		sb.append("    FROM");
		sb.append("         COURSE INNER JOIN CATEGORY");
		sb.append("      ON COURSE.CATEGORY_ID = CATEGORY.CATEGORY_ID");
		sb.append("   WHERE");
		sb.append("         COURSE.CATEGORY_ID = ?");
		sb.append("     AND DELETE_FLG = 0");
		sb.append("   ORDER BY");
		sb.append("         COURSE_ID");


		List<CourseDto> list = new ArrayList<>();
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setInt(1, categoryId);
			// SQL�������s����
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CourseDto dto = new CourseDto();
				dto.setCategory(rs.getString("CATEGORY"));
				dto.setCategoryId(rs.getInt("COURSE.CATEGORY_ID"));
				dto.setCourse(rs.getString("COURSE"));
				dto.setCourseId(rs.getInt("COURSE_ID"));
				dto.setCourseOverview(rs.getString("COURSE_OVERVIEW"));
				dto.setPrerequisite(rs.getString("PREREQUISITE"));
				dto.setGoal(rs.getString("GOAL"));
				dto.setEstimatedStudyTime(rs.getInt("ESTIMATED_STUDY_TIME"));
				dto.setUpdateOperatorId(rs.getInt("COURSE.UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("COURSE.UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("COURSE.UPDATE_NUMBER"));
				dto.setIsFreeCourseFlg(rs.getBoolean("FREE_COURSE_FLG"));
				dto.setDeleteFlg(rs.getBoolean("DELETE_FLG"));
				
				//�@�R�[�X���Ƃ̖ڎ��ꗗ���擾����
				IndexDao indexDao = new IndexDao(conn);
				
				List<IndexDto> indexList = indexDao.selectByCourseId(rs.getInt("COURSE_ID"));
				
				StringBuffer indexs = new StringBuffer();
				for (IndexDto index : indexList) {
					indexs.append(index.getIndexs());
					indexs.append(" ");
				}
				dto.setIndexs(indexs.toString());
				
				list.add(dto);
			}
			return list;
		}
	}

	
	/**
	 * �R�[�X�����擾����
	 * @return �R�[�X���
	 * @throws SQLException SQL��O
	 */
	public CourseDto selectByCourseId(int courseId) throws SQLException {

		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("         CATEGORY");
		sb.append("        ,COURSE.CATEGORY_ID");
		sb.append("        ,COURSE");
		sb.append("        ,COURSE_ID");
		sb.append("        ,COURSE_OVERVIEW");
		sb.append("        ,PREREQUISITE");
		sb.append("        ,GOAL");
		sb.append("        ,ESTIMATED_STUDY_TIME");
		sb.append("        ,COURSE.UPDATE_OPERATOR_ID");
		sb.append("        ,COURSE.UPDATE_AT");
		sb.append("        ,COURSE.UPDATE_NUMBER");
		sb.append("        ,FREE_COURSE_FLG");
		sb.append("    FROM");
		sb.append("         COURSE INNER JOIN CATEGORY");
		sb.append("      ON COURSE.CATEGORY_ID = CATEGORY.CATEGORY_ID");
		sb.append("   WHERE");
		sb.append("         COURSE_ID = ?");


    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setInt(1, courseId);
			// SQL�������s����
			ResultSet rs = ps.executeQuery();
			CourseDto dto = new CourseDto();
			while (rs.next()) {
				dto.setCategory(rs.getString("CATEGORY"));
				dto.setCategoryId(rs.getInt("COURSE.CATEGORY_ID"));
				dto.setCourse(rs.getString("COURSE"));
				dto.setCourseId(rs.getInt("COURSE_ID"));
				dto.setCourseOverview(rs.getString("COURSE_OVERVIEW"));
				dto.setPrerequisite(rs.getString("PREREQUISITE"));
				dto.setGoal(rs.getString("GOAL"));
				dto.setEstimatedStudyTime(rs.getInt("ESTIMATED_STUDY_TIME"));
				dto.setUpdateOperatorId(rs.getInt("COURSE.UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("COURSE.UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("COURSE.UPDATE_NUMBER"));
				dto.setIsFreeCourseFlg(rs.getBoolean("FREE_COURSE_FLG"));
				
				//�@�R�[�X���Ƃ̖ڎ��ꗗ���擾����
				IndexDao indexDao = new IndexDao(conn);
				
				List<IndexDto> indexList = indexDao.selectByCourseId(rs.getInt("COURSE_ID"));
				
				StringBuffer indexs = new StringBuffer();
				for (IndexDto index : indexList) {
					indexs.append(index.getContent());
					indexs.append("�A");
				}
				dto.setIndexs(indexs.toString());
				
			}
			return dto;
		}
	}
	
	
	/**
	 * �R�[�X���쐬����
	 * @param dto �R�[�X
	 * @return 
	 * @return �X�V����
	 * @throws SQLException SQL��O
	 */
	public int insert(CourseDto dto) throws SQLException {
    	// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into   COURSE");
		sb.append("           (");
		sb.append("               COURSE");
		sb.append("              ,CATEGORY_ID");
		sb.append("              ,ESTIMATED_STUDY_TIME");
		sb.append("              ,COURSE_OVERVIEW");
		sb.append("              ,PREREQUISITE");
		sb.append("              ,GOAL");		
		sb.append("              ,UPDATE_OPERATOR_ID");
		sb.append("              ,UPDATE_NUMBER");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("               ?");
		sb.append("              ,?");
		sb.append("              ,?");
		sb.append("              ,?");
		sb.append("              ,?");		
		sb.append("              ,?");
		sb.append("              ,?");		
		sb.append("              ,0");
		sb.append("           )");
		
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

			// �v���[�X�z���_�[�ɒl���Z�b�g����
			ps.setString(1, dto.getCourse());
			ps.setInt(2, dto.getCategoryId());
			ps.setInt(3, dto.getEstimatedStudyTime());
			ps.setString(4, dto.getCourseOverview());
			ps.setString(5, dto.getPrerequisite());
			ps.setString(6, dto.getGoal());
			ps.setInt(7, dto.getUpdateOperatorId());
			
			// SQL�����s����
			return ps.executeUpdate();
		}		
		
	}

	
	/**
	 * �R�[�X�����X�V����
	 * @param dto �R�[�X���
	 * @return �X�V����
	 * @throws SQLException SQL��O
	 */
	public int update(CourseDto dto) throws SQLException{
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        COURSE");
		sb.append("    set");
		sb.append("        CATEGORY_ID = ?");
		sb.append("       ,COURSE = ?");	
		sb.append("       ,ESTIMATED_STUDY_TIME = ?");
		sb.append("       ,COURSE_OVERVIEW = ?");
		sb.append("       ,PREREQUISITE = ?");
		sb.append("       ,GOAL = ?");			
		sb.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("       ,UPDATE_AT = CURRENT_TIMESTAMP()");
		sb.append("  where COURSE_ID = ?");
		sb.append("    and UPDATE_NUMBER = ?");	
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setInt(1, dto.getCategoryId());
			ps.setString(2, dto.getCourse());
			ps.setInt(3, dto.getEstimatedStudyTime());
			ps.setString(4, dto.getCourseOverview());
			ps.setString(5, dto.getPrerequisite());
			ps.setString(6, dto.getGoal());
			ps.setInt(7, dto.getCourseId());
			ps.setInt(8, dto.getUpdateNumber());
			
            // SQL�����s����
			return ps.executeUpdate();			
		}
		
	}

	public int deleteByCourseId(CourseDto dto) throws SQLException{
		
		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        COURSE");
		sb.append("    SET");
		sb.append("        delete_flg = 1");
		sb.append("    WHERE");
		sb.append("        course_id = ?");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
		// �v���[�X�z���_�[�ɒl���Z�b�g����
		ps.setInt(1, dto.getCourseId());

		// SQL�����s����
		return ps.executeUpdate();
		}		
		
	}

	

}
