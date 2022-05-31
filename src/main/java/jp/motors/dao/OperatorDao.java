package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

import jp.motors.dto.OperatorDto;

public class OperatorDao {

	private Connection conn;

	/**
	 * �R���X�g���N�^
	 * �R�l�N�V�������t�B�[���h�ɐݒ肷��
	 * @param conn �R�l�N�V����
	 */
	public OperatorDao(Connection conn) {
        this.conn = conn;
    }
	
	
	public List<OperatorDto> selectAll() throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        OPERATOR_ID");
		sb.append("        ,EMAIL");
		sb.append("        ,PASSWORD");
		sb.append("        ,FIRST_NAME");
		sb.append("        ,LAST_NAME");
		sb.append("        ,AUTHORITY");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("        ,DELETE_FLG");
		sb.append("    FROM");
		sb.append("        OPERATOR");
		sb.append("    where DELETE_FLG = 0;");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	//���s
        	ResultSet rs = ps.executeQuery();
        	
        	// ���ʂ�List�ɋl�߂�
        	List<OperatorDto> operatorList = new ArrayList<>();
        	while (rs.next()) {
        		OperatorDto dto = new OperatorDto();
        		dto.setOperatorId(rs.getInt("operator_id"));
        		dto.setEmail(rs.getString("email"));
        		dto.setFirstName(rs.getString("first_name"));
        		dto.setLastName(rs.getString("last_name"));
        		dto.setAuthority(rs.getInt("authority"));
        		dto.setUpdateOperatorId(rs.getInt("operator_id"));
        		dto.setUpdateAt(rs.getTimestamp("update_at"));
        		dto.setUpdateNumber(rs.getInt("update_number"));
        		dto.setDeleteFlg(rs.getBoolean("delete_flg"));
        		operatorList.add(dto);
        	}
        	return operatorList;
        }
		
		
	}
	
public List<OperatorDto> selectNotMyId(int operatorId) throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        OPERATOR_ID");
		sb.append("        ,EMAIL");
		sb.append("        ,PASSWORD");
		sb.append("        ,FIRST_NAME");
		sb.append("        ,LAST_NAME");
		sb.append("        ,AUTHORITY");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("        ,DELETE_FLG");
		sb.append("    FROM");
		sb.append("        OPERATOR");
		sb.append("        WHERE NOT   ");
		sb.append("        OPERATOR_ID = ?;");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	// �X�e�C�g�����g�Z�b�g
            ps.setInt(1, operatorId);
        	
        	//���s
        	ResultSet rs = ps.executeQuery();
        	
        	// ���ʂ�List�ɋl�߂�
        	List<OperatorDto> operatorList = new ArrayList<>();
        	while (rs.next()) {
        		OperatorDto dto = new OperatorDto();
        		dto.setOperatorId(rs.getInt("operator_id"));
        		dto.setEmail(rs.getString("email"));
        		dto.setFirstName(rs.getString("first_name"));
        		dto.setLastName(rs.getString("last_name"));
        		dto.setAuthority(rs.getInt("authority"));
        		dto.setUpdateOperatorId(rs.getInt("operator_id"));
        		dto.setUpdateAt(rs.getTimestamp("update_at"));
        		dto.setUpdateNumber(rs.getInt("update_number"));
        		dto.setDeleteFlg(rs.getBoolean("delete_flg"));
        		operatorList.add(dto);
        	}
        	return operatorList;
        }
		
		
	}
	
	
	
	
	/**
     * ���[�U�����擾����
     * @param id ID
     * @param password �p�X���[�h
     * @return ���[�U���
     * @throws SQLException SQL��O
     */
	public OperatorDto findByIdAndPassword(String id, String password) throws SQLException {
		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
    	sb.append(" select");
    	sb.append("        OPERATOR_ID");
    	sb.append("       ,EMAIL");
    	sb.append("       ,FIRST_NAME");
    	sb.append("       ,LAST_NAME");
    	sb.append("       ,AUTHORITY");
    	sb.append("       ,UPDATE_OPERATOR_ID");
    	sb.append("       ,UPDATE_AT");
    	sb.append("       ,UPDATE_NUMBER");
    	sb.append("   from OPERATOR");
    	sb.append("  where EMAIL = ?");
    	sb.append("    and PASSWORD = sha2(?, 256)");
    	sb.append("    and DELETE_FLG = 0");
    	
		
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, id);
            ps.setString(2, password);
            
            // SQL�����s����
            ResultSet rs = ps.executeQuery();
            
            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
                OperatorDto operator = new OperatorDto();
                operator.setOperatorId(rs.getInt("OPERATOR_ID"));
                operator.setEmail(rs.getString("EMAIL"));
                operator.setFirstName(rs.getString("FIRST_NAME"));
                operator.setLastName(rs.getString("LAST_NAME"));
                operator.setAuthority(rs.getInt("AUTHORITY"));
                operator.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
                operator.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
                operator.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                return operator;
            }
            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
        	return null;
        }
	}
	
	public List<Boolean> findResetPasswordById(List<OperatorDto> dtoList) throws SQLException {

    	// SQL�����쐬����
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select");
    	sb.append("        OPERATOR_ID");
    	sb.append("   from OPERATOR");
    	sb.append("  where OPERATOR_ID = ?");
    	sb.append("        AND password = sha2(");
    	sb.append("            \"codetrain123\"");
    	sb.append("            ,256");
    	sb.append("        );");
    	List<Boolean> resetPasswordList = new ArrayList<>();
    	
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
        	for (OperatorDto operatorDto: dtoList) {
        	
        		 ps.setInt(1, operatorDto.getOperatorId());
                 
        		 ResultSet rs = ps.executeQuery();
        		 if (!rs.next()){
        			 resetPasswordList.add(false);
        		} else {
        			 resetPasswordList.add(true);
        		}
        		 
        		 
        	}
        	 return resetPasswordList;
        }
	}
	
	//ID�ƃp�X���[�h�̓��̓`�F�b�N
	public OperatorDto findPasswordByuserIdPassword(int OperatorId,String oldPassword) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        Operator_id");
		sb.append("    FROM");
		sb.append("        Operator");
		sb.append("    WHERE");
		sb.append("        Operator_id = ?");
		sb.append("        AND password = sha2(");
		sb.append("            ?");
		sb.append("            ,256");
		sb.append("        )");
		sb.append(";");


		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, OperatorId);
            ps.setString(2, oldPassword);
            
            // SQL�����s����
            ResultSet rs = ps.executeQuery();
            
            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
            	OperatorDto operator = new OperatorDto();
            	operator.setOperatorId(rs.getInt("OPERATOR_ID"));
                return operator;
            }
            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
        	return null;
        }
	}
	

	
	
	
	
	
			 
		public void insert(OperatorDto dto) throws SQLException {
			
			//SQL�����`
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT");
			sb.append("    INTO");
			sb.append("        OPERATOR(");
			sb.append("            EMAIL");
			sb.append("            ,PASSWORD");
			sb.append("            ,FIRST_NAME");
			sb.append("            ,LAST_NAME");
			sb.append("            ,AUTHORITY");
			sb.append("            ,UPDATE_OPERATOR_ID");
			sb.append("            ,UPDATE_NUMBER");
			sb.append("            ,DELETE_FLG");
			sb.append("            ,UPDATE_AT");
			sb.append("        )");
			sb.append("    VALUES");
			sb.append("        (");
			sb.append("            ?");
			sb.append("            ,sha2(");
			sb.append("                ?");
			sb.append("                ,256");
			sb.append("            )");
			sb.append("            ,?");
			sb.append("            ,?");
			sb.append("            ,?");
			sb.append("            ,?");
			sb.append("            ,0");
			sb.append("            ,'0'");
			sb.append("            ,TIMESTAMP(NOW() + INTERVAL 30 MINUTE)");
			sb.append("        );");

			// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
	        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// �v���[�X�z���_�[�ɒl���Z�b�g����
	            ps.setString(1, dto.getEmail());
	            ps.setString(2, dto.getPassword());
	            ps.setString(3, dto.getFirstName());
	            ps.setString(4, dto.getLastName());
	            ps.setInt(5, dto.getAuthority());
	            ps.setInt(6, dto.getUpdateOperatorId());
	            
	            
	            // SQL�����s����
	            ps.executeUpdate();
	            
		}
	}

		
	public void updateOperator(OperatorDto dto) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        OPERATOR");
		sb.append("    SET");
		sb.append("        EMAIL = ?");
		sb.append("        ,FIRST_NAME = ?");
		sb.append("        ,last_name = ?");
		sb.append("        ,AUTHORITY = ?");
		sb.append("        ,UPDATE_OPERATOR_ID = ?");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("    WHERE");
		sb.append("        operator_id = ?;");
		
		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getFirstName());
            ps.setString(3, dto.getLastName());
            ps.setInt(4, dto.getAuthority());
            ps.setInt(5, dto.getUpdateOperatorId());
            ps.setInt(6, dto.getOperatorId());
            
            
            // SQL�����s����
            ps.executeUpdate();
        }   
		
	}
	
	public int updatePassword(OperatorDto dto) throws SQLException {
		
		//SQL���̐��`
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        OPERATOR");
		sb.append("    SET");
		sb.append("        PASSWORD = SHA2(");
		sb.append("            ?");
		sb.append("            ,256");
		sb.append("        )");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("    WHERE");
		sb.append("        OPERATOR_ID = ?;");

		
		// �X�e�C�g�����g�쐬
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// �v���[�X�z���_�[�ɒl���Z�b�g����
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getOperatorId());
	      
	            //SQL�����s
	            return ps.executeUpdate();
		 }
		
	}
	
	public void deleteDelFlg(OperatorDto dto) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        OPERATOR");
		sb.append("    SET");
		sb.append("        DELETE_FLG = 1");
		sb.append("        ,UPDATE_OPERATOR_ID = ?");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("        ,UPDATE_AT = CURRENT_TIMESTAMP()");
		sb.append("    WHERE");
		sb.append("        OPERATOR_ID = ?;");
		
		

		// �X�e�C�g�����g�쐬
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			 
			 //�v���[�X�z���_�[�ɃZ�b�g
			 ps.setInt(1,  dto.getUpdateOperatorId());
			 ps.setInt(2, dto.getOperatorId());
			 
			 //SQL�����s
			 ps.executeUpdate();
		 }
		
	}
	
    public boolean resetCheck(String id, String password) throws SQLException {

    	// SQL�����쐬����
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select");
    	sb.append("        OPERATOR_ID");
    	sb.append("   from operator");
    	sb.append("  where EMAIL = ?");
    	sb.append("        AND password = sha2(");
    	sb.append("            \"codetrain123\"");
    	sb.append("            ,256");
    	sb.append("        )");
    	sb.append("    and UPDATE_AT > TIMESTAMP(NOW())");	
    
    	
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, id);
            
            // SQL�����s����
            ResultSet rs = ps.executeQuery();
            
            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
                OperatorDto operator = new OperatorDto();
                operator.setOperatorId(rs.getInt("Operator_ID"));
                return true;
            }
            // �Y������f�[�^���Ȃ��ꍇ��false��ԋp����
        	return false;
        }
    }
    
	
	
	public void resetPassword(OperatorDto dto) throws SQLException {
		
		//SQL������
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        OPERATOR");
		sb.append("    SET");
		sb.append("        PASSWORD = SHA2(");
		sb.append("            ?");
		sb.append("            ,256");
		sb.append("        )");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("        ,UPDATE_AT = TIMESTAMP(NOW() + INTERVAL 30 MINUTE)");
		sb.append("        ,UPDATE_OPERATOR_ID = ?");
		sb.append("    WHERE");
		sb.append("        OPERATOR_ID = ?;");
		
		
		// �X�e�C�g�����g�쐬
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// �v���[�X�z���_�[�ɒl���Z�b�g����
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getUpdateOperatorId());
	            ps.setInt(3, dto.getOperatorId());
	          
		 
	            //SQL�����s
	            ps.executeUpdate();
		 }

	}
}
