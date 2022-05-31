package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.CategoryDto;

public class CategoryDao {

	/** �R�l�N�V���� */
	protected Connection conn;

	/**
	 * �R���X�g���N�^
	 * �R�l�N�V�������t�B�[���h�ɐݒ肷��
	 * @param conn �R�l�N�V����
	 */
	public CategoryDao(Connection conn) {
        this.conn = conn;
    }
	
	
	
	
	/**
	 * �J�e�S�����쐬����
	 * @param dto �J�e�S��
	 * @return �X�V����
	 * @throws SQLException SQL��O
	 */
	public int insert(CategoryDto dto) throws SQLException {
    	// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into   CATEGORY");
		sb.append("           (");
		sb.append("               CATEGORY");
		sb.append("              ,UPDATE_OPERATOR_ID");
		sb.append("              ,UPDATE_NUMBER");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("               ?");
		sb.append("              ,?");
		sb.append("              ,0");
		sb.append("           )");
		
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

			// �v���[�X�z���_�[�ɒl���Z�b�g����
			ps.setString(1, dto.getCategory());
			ps.setInt(2, dto.getUpdateOperatorId());

			// SQL�����s����
			return ps.executeUpdate();
		}		
		
	}
	
	/**
	 * �J�e�S�������擾����
	 * @return �J�e�S����񃊃X�g
	 * @throws SQLException SQL��O
	 */
	public List<CategoryDto> selectAll() throws SQLException {

		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("   select");
		sb.append("          CATEGORY_ID");
		sb.append("         ,CATEGORY");
		sb.append("         ,UPDATE_OPERATOR_ID");
		sb.append("         ,UPDATE_AT");
		sb.append("         ,UPDATE_NUMBER");
		sb.append("     from CATEGORY");
		sb.append(" order by CATEGORY_ID");

		List<CategoryDto> list = new ArrayList<>();
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
		try (Statement stmt = conn.createStatement()) {

			// SQL�������s����
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				CategoryDto dto = new CategoryDto();
				dto.setCategoryId(rs.getInt("CATEGORY_ID"));
				dto.setCategory(rs.getString("CATEGORY"));
				dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
				list.add(dto);
			}
			return list;
		}
	}



	/**
	 * �J�e�S�������擾����
	 * @param categoryId�@�J�e�S��ID
	 * @return �J�e�S�����
	 * @throws SQLException SQL��O
	 */
	public CategoryDto selectByCategoryId(int categoryId) throws SQLException {
		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        CATEGORY_ID");
		sb.append("       ,CATEGORY");
		sb.append("       ,UPDATE_OPERATOR_ID");
		sb.append("       ,UPDATE_AT");
		sb.append("       ,UPDATE_NUMBER");
		sb.append("   from CATEGORY");
		sb.append("  where CATEGORY_ID = ?");
		
		CategoryDto dto = null;
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setInt(1, categoryId);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				dto = new CategoryDto();
				dto.setCategoryId(rs.getInt("CATEGORY_ID"));
				dto.setCategory(rs.getString("CATEGORY"));
				dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));				
			}
		}
		return dto;
	}



	/**
	 * �J�e�S�������X�V����
	 * @param dto �J�e�S�����
	 * @return �X�V����
	 * @throws SQLException SQL��O
	 */
	public int update(CategoryDto dto) throws SQLException{
		
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        CATEGORY");
		sb.append("    set");
		sb.append("        CATEGORY = ?");
		sb.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("       ,UPDATE_AT = CURRENT_TIMESTAMP()");
		sb.append("  where CATEGORY_ID = ?");
		sb.append("    and UPDATE_NUMBER = ?");		
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setString(1, dto.getCategory());
			ps.setInt(2, dto.getCategoryId());
			ps.setInt(3, dto.getUpdateNumber());
			
			return ps.executeUpdate();
		}
	}




	public CategoryDto checkCategory(String category) throws SQLException{
		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        CATEGORY_ID");
		sb.append("       ,CATEGORY");
		sb.append("       ,UPDATE_OPERATOR_ID");
		sb.append("       ,UPDATE_AT");
		sb.append("       ,UPDATE_NUMBER");
		sb.append("   from CATEGORY");
		sb.append("  where CATEGORY = ?");		
		
		CategoryDto dto = new CategoryDto();
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setString(1, category);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				dto.setCategoryId(rs.getInt("CATEGORY_ID"));
				dto.setCategory(rs.getString("CATEGORY"));
				dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));				
			}
		}
		return dto;
	}
	
	
}
