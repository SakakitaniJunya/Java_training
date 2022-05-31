package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.ResultDto;

public class ResultDao {

	/** �R�l�N�V���� */
	protected Connection conn;

	/**
	 * �R���X�g���N�^
	 * �R�l�N�V�������t�B�[���h�ɐݒ肷��
	 * @param conn �R�l�N�V����
	 */
	public ResultDao(Connection conn) {
        this.conn = conn;
    }
	
	
	public int insert(ResultDto resultDto) throws SQLException {
		// SQL���쐬
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO RESULT(");
		sb.append("            USER_ID");
		sb.append("           ,PROBLEM_ID");
		sb.append("           ,SELECTED_SELECTION_ID");
		sb.append("           )");
		sb.append("     VALUES");
		sb.append("           (");
		sb.append("             ?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("           )");

		// preparedstatement�I�u�W�F�N�g�쐬
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			// �v���[�X�z���_�ɒl���Z�b�g����
			ps.setInt(1, resultDto.getUserId());
			ps.setInt(2, resultDto.getProblemId());
			ps.setInt(3, resultDto.getSelectedSelectionId());
			
			//�@SQL�����s����
			return ps.executeUpdate();
		}
	}
	
	public List<ResultDto> selectByUserIdAndCourseId(int userId, int courseId) throws SQLException {
		
		// ��ql���쐬
		StringBuffer sb1 = new StringBuffer();
		sb1.append("      SELECT");
		sb1.append("             PROBLEM_ID");
		sb1.append("        FROM");
		sb1.append("             PROBLEM");
		sb1.append("        WHERE");
		sb1.append("             COURSE_ID = ?");
		
		List<Integer> problemIdList = new ArrayList<>();
		
		// preparedstatement�I�u�W�F�N�g�쐬
		try (PreparedStatement ps = conn.prepareStatement(sb1.toString())){
			// �v���[�X�z���_�ɒl���Z�b�g����
			ps.setInt(1, courseId);
			
			//�@SQL�����s����
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				problemIdList.add(rs.getInt("PROBLEM_ID"));
			}
		}
		
		List<ResultDto> list = new ArrayList<>();
		
		for (int problemId : problemIdList) {
			
			// sql���쐬
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT");
			sb.append("         USER_ID");
			sb.append("        ,PROBLEM_ID");
			sb.append("        ,SELECTED_SELECTION_ID");
			sb.append("        ,TEST_AT");
			sb.append("    FROM");
			sb.append("         RESULT");
			sb.append("    WHERE");
			sb.append("         RESULT_ID = (");
			sb.append("         SELECT");
			sb.append("                MIN(RESULT_ID)");
			sb.append("           FROM");
			sb.append("                RESULT");
			sb.append("          WHERE");
			sb.append("                USER_ID = ?");
			sb.append("            AND PROBLEM_ID = ?");
			sb.append("            AND TEST_AT = (");
			sb.append("                 SELECT");
			sb.append("                      MAX(TEST_AT)");
			sb.append("                FROM");
			sb.append("                      RESULT");
			sb.append("               WHERE");
			sb.append("                      USER_ID = ?");
			sb.append("                  AND PROBLEM_ID = ?");
			sb.append("                    )");
			sb.append("        )");

			// preparedstatement�I�u�W�F�N�g�쐬
			try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
				// �v���[�X�z���_�ɒl���Z�b�g����
				ps.setInt(1, userId);
				ps.setInt(2, problemId);
				ps.setInt(3, userId);
				ps.setInt(4, problemId);
				
				//�@SQL�����s����
				
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					ResultDto resultDto = new ResultDto();
					resultDto.setUserId(rs.getInt("USER_ID"));
					resultDto.setProblemId(rs.getInt("PROBLEM_ID"));
					resultDto.setSelectedSelectionId(rs.getInt("SELECTED_SELECTION_ID"));
					resultDto.setTestAt(rs.getTimestamp("TEST_AT"));
						list.add(resultDto);
				}
					
			}
		}
		return list;
		
		
	}
	
		
	
	
	
	
}
