package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.SelectionResultDto;

public class SelectionResultDao {

	/** �R�l�N�V���� */
	protected Connection conn;

	/**
	 * �R���X�g���N�^
	 * �R�l�N�V�������t�B�[���h�ɐݒ肷��
	 * @param conn �R�l�N�V����
	 */
	public SelectionResultDao(Connection conn) {
        this.conn = conn;
    }
	
	
	
	public List<SelectionResultDto> selectByProblemId(int problemId) throws SQLException{
		
	
		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("     select");
		sb.append("            SELECTION_RESULT_ID");
		sb.append("           ,PROBLEM_ID");
		sb.append("           ,SELECTION");
		sb.append("           ,RESULT");
		sb.append("       from SELECTION_RESULT");
		sb.append("      where PROBLEM_ID = ?");
		sb.append("   order by SELECTION_RESULT_ID");
		
		List<SelectionResultDto> selectionResultList = new ArrayList<>();
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setInt(1, problemId);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SelectionResultDto dto = new SelectionResultDto();
				dto.setSelectionResultId(rs.getInt("SELECTION_RESULT_ID"));
				dto.setProblemId(rs.getInt("PROBLEM_ID"));
				dto.setSelection(rs.getString("SELECTION"));
				dto.setResult(rs.getString("RESULT"));
				selectionResultList.add(dto);
			}
			
			return selectionResultList;
		}
	}
	
	
	
	public int insert(SelectionResultDto dto) throws SQLException {
		// SQL���쐬
		StringBuffer sb = new StringBuffer();
		sb.append("      INSERT");
		sb.append("        INTO SELECTION_RESULT (");
		sb.append("             PROBLEM_ID");
		sb.append("            ,SELECTION");
		sb.append("            ,RESULT");
		sb.append("            )");
		sb.append("    VALUES");
		sb.append("            (");
		sb.append("             ?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("        );");


		// preparedstatement�I�u�W�F�N�g�쐬
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			// �v���[�X�z���_�ɒl���Z�b�g����
			ps.setInt(1, dto.getProblemId());
			ps.setString(2, dto.getSelection());
			ps.setString(3, dto.getResult());
			
			//�@SQL�����s����
			return ps.executeUpdate();
		}
	}
	
	public int selectCorrectSelectionId(int correctSelectionNumber) throws SQLException{
		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("     select");
		sb.append("            MAX(SELECTION_RESULT_ID)");
		sb.append("       from SELECTION_RESULT");
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			int maxSelectionResultId = 0;
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				maxSelectionResultId = rs.getInt("MAX(SELECTION_RESULT_ID)");
			}
			
			return (maxSelectionResultId - 4 + correctSelectionNumber);
		}
	}
	
	
	public int update(SelectionResultDto dto) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        SELECTION_RESULT");
		sb.append("    SET");
		sb.append("         SELECTION = ?");
		sb.append("        ,RESULT = ?");
		sb.append("    WHERE");
		sb.append("        SELECTION_RESULT_ID = ?");

	
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			
			ps.setString(1, dto.getSelection());
			ps.setString(2, dto.getResult());
			ps.setInt(3, dto.getSelectionResultId());
			
			return ps.executeUpdate();
		}
		
	}
	
}
