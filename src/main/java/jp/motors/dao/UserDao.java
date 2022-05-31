package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.UserDto;

public class UserDao {

	/** �R�l�N�V���� */
	protected Connection conn;

	/**
	 * �R���X�g���N�^
	 * �R�l�N�V�������t�B�[���h�ɐݒ肷��
	 * @param conn �R�l�N�V����
	 */
	public UserDao(Connection conn) {
        this.conn = conn;
    }
	
	public int deleteByDeleteAt() throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        USER");
		sb.append("    SET");
		sb.append("          EMAIL = ''");
		sb.append("         ,FIRST_NAME = null");
		sb.append("         ,LAST_NAME = ''");
		sb.append("    WHERE");
		sb.append("          DELETE_AT <= TIMESTAMP(NOW())");
			
		// �X�e�C�g�����g�쐬
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			 
		       //SQL�����s
		       return ps.executeUpdate();
		 }
	}
	
	   /**
     * ���[�U�����擾����
     * @param id ID
     * @param password �p�X���[�h
     * @return ���[�U���
     * @throws SQLException SQL��O
     */
    public UserDto findByIdAndPassword(String id, String password) throws SQLException {

    	// SQL�����쐬����
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select");
    	sb.append("        USER_ID");
    	sb.append("       ,CORPORATION_ID");
    	sb.append("       ,EMAIL");
    	sb.append("       ,FIRST_NAME");
    	sb.append("       ,LAST_NAME");
    	sb.append("       ,PAUSE_AT");
    	sb.append("       ,DELETE_AT");
    	sb.append("       ,UPDATE_CORPORATOR_ID");
    	sb.append("       ,UPDATE_AT");
    	sb.append("       ,UPDATE_NUMBER");
    	sb.append("   from USER");
    	sb.append("  where EMAIL = ?");
    	sb.append("    and PASSWORD = sha2(?, 256)");
    	sb.append("    and (DELETE_AT > TIMESTAMP(NOW())");
    	sb.append("     OR DELETE_AT IS NULL)");
    	sb.append("    and (PAUSE_AT > TIMESTAMP(NOW())");
    	sb.append("     OR PAUSE_AT IS NULL)");
    	
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, id);
            ps.setString(2, password);
            
            // SQL�����s����
            ResultSet rs = ps.executeQuery();
            
            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
                UserDto user = new UserDto();
                user.setUserId(rs.getInt("USER_ID"));
                user.setCorporationId(rs.getInt("CORPORATION_ID"));
                user.setEmail(rs.getString("EMAIL"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setPauseAt(rs.getTimestamp("PAUSE_AT"));
                user.setDeleteAt(rs.getTimestamp("DELETE_AT"));
                user.setUpdateCorporatorId(rs.getInt("UPDATE_CORPORATOR_ID"));
                user.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
                user.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                return user;
            }
            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
        	return null;
        }
    }
    
    
    public boolean resetCheck(String id, String password) throws SQLException {

    	// SQL�����쐬����
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select");
    	sb.append("        USER_ID");
    	sb.append("       ,CORPORATION_ID");
    	sb.append("       ,EMAIL");
    	sb.append("       ,FIRST_NAME");
    	sb.append("       ,LAST_NAME");
    	sb.append("       ,PAUSE_AT");
    	sb.append("       ,DELETE_AT");
    	sb.append("       ,UPDATE_CORPORATOR_ID");
    	sb.append("       ,UPDATE_AT");
    	sb.append("       ,UPDATE_NUMBER");
    	sb.append("   from USER");
    	sb.append("  where EMAIL = ?");
    	sb.append("        AND password = sha2(");
    	sb.append("            \"codetrain123\"");
    	sb.append("            ,256");
    	sb.append("        )");
    	sb.append("    and UPDATE_AT > TIMESTAMP(NOW())");	
    	sb.append("    and (DELETE_AT > TIMESTAMP(NOW())");
    	sb.append("     OR DELETE_AT IS NULL)");
    	sb.append("    and (PAUSE_AT > TIMESTAMP(NOW())");
    	sb.append("     OR PAUSE_AT IS NULL)");
    	
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, id);
            
            // SQL�����s����
            ResultSet rs = ps.executeQuery();
            
            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
                UserDto user = new UserDto();
                user.setUserId(rs.getInt("USER_ID"));
                user.setCorporationId(rs.getInt("CORPORATION_ID"));
                user.setEmail(rs.getString("EMAIL"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setPauseAt(rs.getTimestamp("PAUSE_AT"));
                user.setDeleteAt(rs.getTimestamp("DELETE_AT"));
                user.setUpdateCorporatorId(rs.getInt("UPDATE_CORPORATOR_ID"));
                user.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
                user.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                return true;
            }
            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
        	return false;
        }
    }
    
    
    
    
    
    public UserDto findPasswordByuserIdPassword(int userId,String password) throws SQLException  { 
    	
    	

    	    	// SQL�����쐬����
    	    	StringBuffer sb = new StringBuffer();
    	    	sb.append(" select");
    	    	sb.append("        USER_ID");
    	    	sb.append("       ,CORPORATION_ID");
    	    	sb.append("       ,EMAIL");
    	    	sb.append("       ,FIRST_NAME");
    	    	sb.append("       ,LAST_NAME");
    	    	sb.append("       ,PAUSE_AT");
    	    	sb.append("       ,DELETE_AT");
    	    	sb.append("       ,UPDATE_CORPORATOR_ID");
    	    	sb.append("       ,UPDATE_AT");
    	    	sb.append("       ,UPDATE_NUMBER");
    	    	sb.append("   from USER");
    	    	sb.append("  where user_id = ?");
    	    	sb.append("    and PASSWORD = sha2(?, 256)");
    	    	sb.append("    and (DELETE_AT > TIMESTAMP(NOW())");
    	    	sb.append("     OR DELETE_AT IS NULL)");
    	      	sb.append("    and (PAUSE_AT > TIMESTAMP(NOW())");
    	    	sb.append("     OR PAUSE_AT IS NULL)");
    	    	
    	    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
    	        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
    	        	// �v���[�X�z���_�[�ɒl���Z�b�g����
    	            ps.setInt(1, userId);
    	            ps.setString(2, password);
    	            
    	            // SQL�����s����
    	            ResultSet rs = ps.executeQuery();
    	            
    	            // ���ʂ�DTO�ɋl�߂�
    	            if (rs.next()) {
    	                UserDto user = new UserDto();
    	                user.setUserId(rs.getInt("USER_ID"));
    	                user.setCorporationId(rs.getInt("CORPORATION_ID"));
    	                user.setEmail(rs.getString("EMAIL"));
    	                user.setFirstName(rs.getString("FIRST_NAME"));
    	                user.setLastName(rs.getString("LAST_NAME"));
    	                user.setPauseAt(rs.getTimestamp("PAUSE_AT"));
    	                user.setDeleteAt(rs.getTimestamp("DELETE_AT"));
    	                user.setUpdateCorporatorId(rs.getInt("UPDATE_CORPORATOR_ID"));
    	                user.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
    	                user.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
    	                return user;
    	            }
    	            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
    	        	return null;
    	        }
        	
       
    }
    
    
    public List<Boolean> findResetPasswordById(List<UserDto> dtoList) throws SQLException {

    	// SQL�����쐬����
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select");
    	sb.append("        USER_ID");
    	sb.append("   from USER");
    	sb.append("  where USER_ID = ?");
    	sb.append("        AND password = sha2(");
    	sb.append("            \"codetrain123\"");
    	sb.append("            ,256");
    	sb.append("        );");
    	List<Boolean> resetPasswordList = new ArrayList<>();
    	
    	// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
        	for (UserDto userDto: dtoList) {
        	
        		 ps.setInt(1, userDto.getUserId());
                 
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
     
    
    
    /**
	 * ���[�U�̑S���擾
	 * 
	 * @return
	 */
	public List<UserDto> selectAll() throws SQLException {
		
		//SQL���쐬
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        user_id");
		sb.append("        ,corporation_id");
		sb.append("        ,email");
		sb.append("        ,password");
		sb.append("        ,first_name");
		sb.append("        ,last_name");
		sb.append("        ,pause_at");
		sb.append("        ,delete_at");
		sb.append("        ,update_corporator_id");
		sb.append("        ,update_at");
		sb.append("        ,update_number");
		sb.append("   FROM");
		sb.append("        USER");
		sb.append("  WHERE DELETE_AT > TIMESTAMP(NOW())");
		sb.append("     OR DELETE_AT IS NULL");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	// SQL�����s����
            ResultSet rs = ps.executeQuery();
            
            // ���ʂ�List�ɋl�߂�
            List<UserDto> userList = new ArrayList<>();
            while (rs.next()) {
            	UserDto userDto = new UserDto();
            	userDto.setUserId(rs.getInt("user_id"));
            	userDto.setEmail(rs.getString("email"));
            	userDto.setCorporationId(rs.getInt("corporation_id"));
            	userDto.setFirstName(rs.getString("first_name"));
            	userDto.setLastName(rs.getString("last_name"));
            	userDto.setPauseAt(rs.getTimestamp("pause_at"));
            	userDto.setDeleteAt(rs.getTimestamp("delete_at"));
            	userDto.setUpdateCorporatorId(rs.getInt("update_corporator_id"));
            	userDto.setUpdateAt(rs.getTimestamp("update_at"));
            	userDto.setUpdateNumber(rs.getInt("update_number"));
            	userDto.setPassword(rs.getString("password"));
            	userList.add(userDto);
            }
            return userList;
        }
	}
	
	  /**
		 * ���[�U��
		 * 
		 * @return
		 */
		public List<UserDto> selectByUserId(int corporationId) throws SQLException {
			
			//SQL���쐬
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT");
			sb.append("        user_id");
			sb.append("        ,corporation_id");
			sb.append("        ,email");
			sb.append("        ,password");
			sb.append("        ,first_name");
			sb.append("        ,last_name");
			sb.append("        ,pause_at");
			sb.append("        ,delete_at");
			sb.append("        ,update_corporator_id");
			sb.append("        ,update_at");
			sb.append("        ,update_number");
			sb.append("   FROM");
			sb.append("        USER");
			sb.append("  WHERE CORPORATION_ID = ?");
			sb.append("  AND (DELETE_AT > TIMESTAMP(NOW())");
			sb.append("     OR DELETE_AT IS NULL)");

			// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
	        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	
	        	
	        	// �v���[�X�z���_�[�ɒl���Z�b�g����
	            ps.setInt(1, corporationId);
	        	// SQL�����s����
	            ResultSet rs = ps.executeQuery();
	            
	            // ���ʂ�List�ɋl�߂�
	            List<UserDto> userList = new ArrayList<>();
	            while (rs.next()) {
	            	UserDto userDto = new UserDto();
	            	userDto.setUserId(rs.getInt("user_id"));
	            	userDto.setEmail(rs.getString("email"));
	            	userDto.setCorporationId(rs.getInt("corporation_id"));
	            	userDto.setFirstName(rs.getString("first_name"));
	            	userDto.setLastName(rs.getString("last_name"));
	            	userDto.setPauseAt(rs.getTimestamp("pause_at"));
	            	userDto.setDeleteAt(rs.getTimestamp("delete_at"));
	            	userDto.setUpdateCorporatorId(rs.getInt("update_corporator_id"));
	            	userDto.setUpdateAt(rs.getTimestamp("update_at"));
	            	userDto.setUpdateNumber(rs.getInt("update_number"));
	            	userDto.setPassword(rs.getString("password"));
	            	userList.add(userDto);
	            }
	            return userList;
	        }
		}
	
	
	public void insertUser(UserDto dto) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT");
		sb.append("    INTO");
		sb.append("        USER(CORPORATION_ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, UPDATE_CORPORATOR_ID, UPDATE_NUMBER, UPDATE_AT)");
		sb.append("    VALUES");
		sb.append("        (");
		sb.append("            ?");
		sb.append("            ,?");
		sb.append("            ,sha2(?,256)");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,'0'");
		sb.append("            ,TIMESTAMP(NOW() + INTERVAL 30 MINUTE)");
		sb.append("        );");


		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	//�X�e�C�g�����g�Z�b�g
        	ps.setInt(1, dto.getCorporationId());
        	ps.setString(2, dto.getEmail());
        	ps.setString(3, dto.getPassword());
        	ps.setString(4, dto.getFirstName());
        	ps.setString(5, dto.getLastName());
        	ps.setInt(6, dto.getUpdateCorporatorId());
        	// SQL�����s����
            ps.executeUpdate();
        }    
            
	}
	
	public void updateUser(UserDto dto) throws SQLException {
		
		//SQL���쐬
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        USER");
		sb.append("    SET");
		sb.append("        corporation_id = ?");
		sb.append("        ,email = ?");
		sb.append("        ,first_name = ?");
		sb.append("        ,last_name = ?");
		sb.append("        ,pause_at = ?");
		sb.append("        ,delete_at = ?");
		sb.append("        ,update_corporator_id = ?");
		sb.append("        ,update_number = update_number + 1");
		sb.append("    WHERE");
		sb.append("        user_id = ?;");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, dto.getCorporationId());
            ps.setString(2, dto.getEmail());
            ps.setString(3, dto.getFirstName());
            ps.setString(4, dto.getLastName());
            ps.setTimestamp(5, dto.getPauseAt());
            ps.setTimestamp(6, dto.getDeleteAt());
            ps.setInt(7, dto.getUpdateCorporatorId());
            ps.setInt(8, dto.getUserId());
            // SQL�����s����
            ps.executeUpdate();
        }   
		
	}
	
	public UserDto selectupdateAt(int userId) throws SQLException {
		
		//SQL������
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        update_at");
		sb.append("    FROM");
		sb.append("        USER");
		sb.append("    WHERE");
		sb.append("        user_id = ?;");


		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
         	//�X�e�C�g�����g�Z�b�g
        	ps.setInt(1, userId);
        	
        	ResultSet rs = ps.executeQuery();
        	
        	//SQL���s
        	UserDto dto = new UserDto();
            dto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
        	return dto;
        }
		
	}
	
	public int updatePassword(UserDto dto) throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        USER");
		sb.append("    SET");
		sb.append("        password = sha2(");
		sb.append("            ?");
		sb.append("            ,256");
		sb.append("        )");
		sb.append("        ,update_number = update_number + 1");
		sb.append("    WHERE");
		sb.append("        user_id = ?");
		sb.append("        AND update_number = ?;");
		
		// �X�e�C�g�����g�쐬
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// �v���[�X�z���_�[�ɒl���Z�b�g����
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getUserId());
	            ps.setInt(3, dto.getUpdateNumber());
		 
	            //SQL�����s
	            return ps.executeUpdate();
		 }
	}

	
	public int resetPassword(UserDto dto) throws SQLException {
			
			//SQL��
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE");
			sb.append("        USER");
			sb.append("    SET");
			sb.append("        password = sha2(");
			sb.append("            ?");
			sb.append("            ,256");
			sb.append("        )");
			sb.append("        ,update_number = update_number + 1");
			sb.append("        ,UPDATE_AT = TIMESTAMP(NOW() + INTERVAL 30 MINUTE)");
			sb.append("        ,update_corporator_id = ?");
			sb.append("    WHERE");
			sb.append("        user_id = ?;");
			
			// �X�e�C�g�����g�쐬
			 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
		        	// �v���[�X�z���_�[�ɒl���Z�b�g����
		            ps.setString(1, dto.getPassword());
		            ps.setInt(2, dto.getUpdateCorporatorId());
		            ps.setInt(3, dto.getUserId());
		          
			 
		            //SQL�����s
		            return ps.executeUpdate();
			 }
		}
	
	public int deleteByCorporationId(int corporationId) throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        USER");
		sb.append("    SET");
		sb.append("          EMAIL = null");
		sb.append("         ,FIRST_NAME = null");
		sb.append("         ,LAST_NAME = null");
		sb.append("    WHERE");
		sb.append("          CORPORATION_ID = ?");
			
		// �X�e�C�g�����g�쐬
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
		       // �v���[�X�z���_�[�ɒl���Z�b�g����
		       ps.setInt(1, corporationId);
			 
		       //SQL�����s
		       return ps.executeUpdate();
		}
	
	}

}
