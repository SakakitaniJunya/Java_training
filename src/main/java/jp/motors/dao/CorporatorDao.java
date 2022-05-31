package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.CorporatorDto;



public class CorporatorDao {

	private Connection conn;

	/**
	 * �R���X�g���N�^
	 * �R�l�N�V�������t�B�[���h�ɐݒ肷��
	 * @param conn �R�l�N�V����
	 */
	public CorporatorDao(Connection conn) {
        this.conn = conn;
    }
	
	
	public List<CorporatorDto> selectAll() throws SQLException { 
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("         CORPORATION_ID");
		sb.append("        ,CORPORATOR_ID");
		sb.append("        ,EMAIL");
		sb.append("        ,FIRST_NAME");
		sb.append("        ,LAST_NAME");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT;");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	//���s
        	ResultSet rs = ps.executeQuery();
        	
        	// ���ʂ�List�ɋl�߂�
        	List<CorporatorDto> corporatorList = new ArrayList<>();
        	while (rs.next()) {
        		CorporatorDto dto = new CorporatorDto();
        		dto.setCorporatorId(rs.getInt("CORPORATION_ID"));
        		dto.setEmail(rs.getString("EMAIL"));
        		dto.setFirstName(rs.getString("FIRST_NAME"));
        		dto.setLastName(rs.getString("LAST_NAME"));
        		dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
        		dto.setUpdateAt(rs.getTimestamp("update_at"));
        		dto.setUpdateNumber(rs.getInt("update_number"));
        		dto.setCorporationId(rs.getInt("CORPORATOR_ID"));
        		corporatorList.add(dto);
        	}
        	return corporatorList;
        }
		
		
	}
	
	
	/**
	 * �����ȊO��email
	 * @return
	 */
	public List<CorporatorDto> selectNotMyId(int corporatorId) throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATION_ID");
		sb.append("        ,CORPORATOR_ID");
		sb.append("        ,EMAIL");
		sb.append("        ,FIRST_NAME");
		sb.append("        ,LAST_NAME");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("        WHERE NOT   ");
		sb.append("        CORPORATOR_ID = ?;");
		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	ps.setInt(1, corporatorId);
        	
        	//���s
        	ResultSet rs = ps.executeQuery();
        	
        	// ���ʂ�List�ɋl�߂�
        	List<CorporatorDto> corporatorList = new ArrayList<>();
        	while (rs.next()) {
        		CorporatorDto dto = new CorporatorDto();
        		dto.setCorporatorId(rs.getInt("CORPORATION_ID"));
        		dto.setEmail(rs.getString("EMAIL"));
        		dto.setFirstName(rs.getString("FIRST_NAME"));
        		dto.setLastName(rs.getString("LAST_NAME"));
        		dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
        		dto.setUpdateAt(rs.getTimestamp("update_at"));
        		dto.setUpdateNumber(rs.getInt("update_number"));
        		dto.setCorporationId(rs.getInt("CORPORATOR_ID"));
        		corporatorList.add(dto);
        	}
        	return corporatorList;
        }
		
		
	}
	
	
	/**
	 * �����ȊO��email
	 * @return
	 */
	public CorporatorDto selectMyId(int corporatorId) throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATION_ID");
		sb.append("        ,CORPORATOR_ID");
		sb.append("        ,EMAIL");
		sb.append("        ,FIRST_NAME");
		sb.append("        ,LAST_NAME");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("        WHERE    ");
		sb.append("        CORPORATOR_ID = ?;");
		
		CorporatorDto dto = null;
		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	ps.setInt(1, corporatorId);
        	
        	//���s
        	ResultSet rs = ps.executeQuery();
        	
        
        	if (rs.next()) {
        		dto = new CorporatorDto();
        		dto.setCorporatorId(rs.getInt("CORPORATION_ID"));
        		dto.setEmail(rs.getString("EMAIL"));
        		dto.setFirstName(rs.getString("FIRST_NAME"));
        		dto.setLastName(rs.getString("LAST_NAME"));
        		dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
        		dto.setUpdateAt(rs.getTimestamp("update_at"));
        		dto.setUpdateNumber(rs.getInt("update_number"));
        		dto.setCorporationId(rs.getInt("CORPORATOR_ID"));
        		
        	}
        	return dto;
        }
		
		
	}
	
	
	
	
	
	public List<CorporatorDto> selectByCorporationId(int corporationId) throws SQLException { 
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATION_ID");
		sb.append("        ,CORPORATOR_ID");
		sb.append("        ,EMAIL");
		sb.append("        ,FIRST_NAME");
		sb.append("        ,LAST_NAME");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("        WHERE CORPORATION_ID =");
		sb.append("        ?;");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, corporationId);
            
        	//���s
        	ResultSet rs = ps.executeQuery();
        	
        	// ���ʂ�List�ɋl�߂�
        	List<CorporatorDto> corporatorList = new ArrayList<>();
        	while (rs.next()) {
        		CorporatorDto dto = new CorporatorDto();
        		dto.setCorporationId(rs.getInt("CORPORATION_ID"));
        		dto.setEmail(rs.getString("EMAIL"));
        		dto.setFirstName(rs.getString("FIRST_NAME"));
        		dto.setLastName(rs.getString("LAST_NAME"));
        		dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
        		dto.setUpdateAt(rs.getTimestamp("update_at"));
        		dto.setUpdateNumber(rs.getInt("update_number"));
        		dto.setCorporatorId(rs.getInt("CORPORATOR_ID"));
        		corporatorList.add(dto);
        	}
        	return corporatorList;
        }
		
		
	}

	
	
	/**
     * ���[�U�����擾����
     * @param id ID
     * @param password �p�X���[�h
     * @return ���[�U���
     * @throws SQLException SQL��O
     */
	public CorporatorDto findByIdAndPassword(String id, String password) throws SQLException {
		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATION_ACCOUNT.CORPORATOR_ID");
		sb.append("        ,CORPORATION_ACCOUNT.CORPORATION_ID");
		sb.append("        ,CORPORATION_ACCOUNT.EMAIL");
		sb.append("        ,CORPORATION_ACCOUNT.FIRST_NAME");
		sb.append("        ,CORPORATION_ACCOUNT.LAST_NAME");
		sb.append("        ,CORPORATION_ACCOUNT.UPDATE_OPERATOR_ID");
		sb.append("        ,CORPORATION_ACCOUNT.UPDATE_AT");
		sb.append("        ,CORPORATION_ACCOUNT.UPDATE_NUMBER");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("            LEFT JOIN CORPORATION");
		sb.append("                ON CORPORATION_ACCOUNT.CORPORATION_ID = CORPORATION.CORPORATION_ID");
		sb.append("    WHERE");
		sb.append("        EMAIL = ?");
		sb.append("        AND PASSWORD = SHA2(");
		sb.append("            ?");
		sb.append("            ,256");
		sb.append("        )");
		sb.append("        AND START_AT < TIMESTAMP(NOW());");

    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, id);
            ps.setString(2, password);
            
            // SQL�����s����
            ResultSet rs = ps.executeQuery();
            
            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
                CorporatorDto corporator = new CorporatorDto();
                corporator.setCorporatorId(rs.getInt("CORPORATOR_ID"));
                corporator.setCorporationId(rs.getInt("CORPORATION_ID"));
                corporator.setEmail(rs.getString("EMAIL"));
                corporator.setFirstName(rs.getString("FIRST_NAME"));
                corporator.setLastName(rs.getString("LAST_NAME"));
                corporator.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
                corporator.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
                corporator.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                return corporator;
            }
            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
        	return null;
        
        }
	}
	
	/**
	 * �������p�X���[�h�ł��X�V������30���ȓ�
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean resetCheck(String id, String password) throws SQLException {

	    	// SQL�����쐬����
	    	StringBuffer sb = new StringBuffer();
	    	sb.append(" select");
	    	sb.append("        CORPORATOR_ID");
	    	sb.append("   from corporation_account");
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
	            	CorporatorDto corporator = new CorporatorDto();
	            	corporator.setCorporatorId(rs.getInt("corporator_ID"));
	                return true;
	            }
	            // �Y������f�[�^���Ȃ��ꍇ��false��ԋp����
	        	return false;
	        }
	    }
	
	
	public void insert(CorporatorDto dto) throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT");
		sb.append("    INTO");
		sb.append("        CORPORATION_ACCOUNT(");
		sb.append("            CORPORATION_ID");
		sb.append("            ,EMAIL");
		sb.append("            ,PASSWORD");
		sb.append("            ,FIRST_NAME");
		sb.append("            ,LAST_NAME");
		sb.append("            ,UPDATE_OPERATOR_ID");
		sb.append("            ,UPDATE_NUMBER");
		sb.append("            ,UPDATE_AT");
		sb.append("        )");
		sb.append("    VALUES");
		sb.append("        (");
		sb.append("            ?");
		sb.append("            ,?");
		sb.append("            ,SHA2(");
		sb.append("                ?");
		sb.append("                ,256");
		sb.append("            )");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,0");
		sb.append("            ,TIMESTAMP(NOW() + INTERVAL 30 MINUTE)");
		sb.append("        );");

		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, dto.getCorporationId());
            ps.setString(2, dto.getEmail());
            ps.setString(3, dto.getPassword());
            ps.setString(4, dto.getFirstName());
            ps.setString(5, dto.getLastName());
            ps.setInt(6, dto.getUpdateOperatorId());
            
            // ���s
            ps.executeUpdate();
        }
		
	}
	
	
	public void update(CorporatorDto dto) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("    SET");
		sb.append("        EMAIL = ?");
		sb.append("        ,FIRST_NAME = ?");
		sb.append("        ,LAST_NAME = ?");
		sb.append("        ,UPDATE_OPERATOR_ID = ?");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("        ,UPDATE_AT = TIMESTAMP(NOW())");
		sb.append("    WHERE");
		sb.append("        CORPORATOR_ID = ?;");

		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getFirstName());
            ps.setString(3, dto.getLastName());
            ps.setInt(4, dto.getUpdateOperatorId());
            ps.setInt(5, dto.getCorporatorId());
            
            // SQL�����s����
            ps.executeUpdate();
        }   
		
	}
	
	
	public void updateNotEmail(CorporatorDto dto) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("    SET");
		sb.append("        FIRST_NAME = ?");
		sb.append("        ,LAST_NAME = ?");
		sb.append("        ,UPDATE_OPERATOR_ID = ?");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("        ,UPDATE_AT = TIMESTAMP(NOW())");
		sb.append("    WHERE");
		sb.append("        CORPORATOR_ID = ?;");

		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, dto.getFirstName());
            ps.setString(2, dto.getLastName());
            ps.setInt(3, dto.getUpdateOperatorId());
            ps.setInt(4, dto.getCorporatorId());
            
            // SQL�����s����
            ps.executeUpdate();
        }   
		
	}
	
	
	
	public CorporatorDto findPasswordByuserIdPassword(int corporatorId,String oldPassword) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATOR_ID");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("    WHERE");
		sb.append("        CORPORATOR_ID = ?");
		sb.append("        AND PASSWORD = SHA2(");
		sb.append("            ?");
		sb.append("            ,256");
		sb.append("        );");

		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, corporatorId);
            ps.setString(2, oldPassword);
            
            // SQL�����s����
            ResultSet rs = ps.executeQuery();
            
            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
            	CorporatorDto corporator = new CorporatorDto();
            	corporator.setCorporatorId(rs.getInt("CORPORATOR_ID"));
                return corporator;
            }
            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
        	return null;
        }
	}
	
	public int updatePassword(CorporatorDto dto) throws SQLException {
		
		//SQL���̐��`
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        corporation_account");
		sb.append("    SET");
		sb.append("        password = sha2(");
		sb.append("            ?");
		sb.append("            ,256");
		sb.append("        )");
		sb.append("        ,update_number = update_number + 1");
		sb.append("    WHERE");
		sb.append("        corporator_id = ?;");
		
		// �X�e�C�g�����g�쐬
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// �v���[�X�z���_�[�ɒl���Z�b�g����
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getCorporatorId());
	      
	            //SQL�����s
	            return ps.executeUpdate();
		 }
		
	}
	
	
	public void resetPassword(CorporatorDto dto) throws SQLException {
		
		//SQL������
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("    SET");
		sb.append("        PASSWORD = SHA2(");
		sb.append("            ?");
		sb.append("            ,256");
		sb.append("        )");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("        ,UPDATE_AT = TIMESTAMP(NOW() + INTERVAL 30 MINUTE)");
		sb.append("        ,UPDATE_OPERATOR_ID = ?");
		sb.append("    WHERE");
		sb.append("        CORPORATOR_ID = ?;");
		
		
		// �X�e�C�g�����g�쐬
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// �v���[�X�z���_�[�ɒl���Z�b�g����
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getUpdateOperatorId());
	            ps.setInt(3, dto.getCorporatorId());
	          
		 
	            //SQL�����s
	            ps.executeUpdate();
		 }

	}
	
	
	public List<Boolean> findResetPasswordById(List<CorporatorDto> dtoList) throws SQLException {

    	// SQL�����쐬����
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select");
    	sb.append("        CORPORATOR_ID");
    	sb.append("   from CORPORATION_ACCOUNT");
    	sb.append("  where CORPORATOR_ID = ?");
    	sb.append("        AND password = sha2(");
    	sb.append("            \"codetrain123\"");
    	sb.append("            ,256");
    	sb.append("        );");
    	
    	List<Boolean> resetPasswordList = new ArrayList<>();
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
        	for (CorporatorDto corporatorDto: dtoList) {
        	
        		 ps.setInt(1, corporatorDto.getCorporatorId());
                 
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
	
	
	
	
	public void deleteById(int corporatorId) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("    WHERE");
		sb.append("        CORPORATOR_ID = ?;");


		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, corporatorId);
            
            // SQL�����s����
            ps.executeUpdate();
        }   
		
	}
	
	
	public int deleteByCorporationId(int corporationId) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("    WHERE");
		sb.append("        CORPORATION_ID = ?;");


		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, corporationId);
            
            // SQL�����s����
           return ps.executeUpdate();
        }   
		
	}
			
}
