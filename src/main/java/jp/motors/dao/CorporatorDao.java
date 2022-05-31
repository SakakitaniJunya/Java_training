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
	 * コンストラクタ
	 * コネクションをフィールドに設定する
	 * @param conn コネクション
	 */
	public CorporatorDao(Connection conn) {
        this.conn = conn;
    }
	
	
	public List<CorporatorDto> selectAll() throws SQLException { 
		
		//SQL文
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

		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	//実行
        	ResultSet rs = ps.executeQuery();
        	
        	// 結果をListに詰める
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
	 * 自分以外のemail
	 * @return
	 */
	public List<CorporatorDto> selectNotMyId(int corporatorId) throws SQLException {
		
		//SQL文
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
		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	ps.setInt(1, corporatorId);
        	
        	//実行
        	ResultSet rs = ps.executeQuery();
        	
        	// 結果をListに詰める
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
	 * 自分以外のemail
	 * @return
	 */
	public CorporatorDto selectMyId(int corporatorId) throws SQLException {
		
		//SQL文
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
		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	ps.setInt(1, corporatorId);
        	
        	//実行
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
		
		//SQL文
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

		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	// プレースホルダーに値をセットする
            ps.setInt(1, corporationId);
            
        	//実行
        	ResultSet rs = ps.executeQuery();
        	
        	// 結果をListに詰める
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
     * ユーザ情報を取得する
     * @param id ID
     * @param password パスワード
     * @return ユーザ情報
     * @throws SQLException SQL例外
     */
	public CorporatorDto findByIdAndPassword(String id, String password) throws SQLException {
		// SQL文を作成する
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

    	// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setString(1, id);
            ps.setString(2, password);
            
            // SQLを実行する
            ResultSet rs = ps.executeQuery();
            
            // 結果をDTOに詰める
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
            // 該当するデータがない場合はnullを返却する
        	return null;
        
        }
	}
	
	/**
	 * 初期化パスワードでかつ更新時刻が30分以内
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean resetCheck(String id, String password) throws SQLException {

	    	// SQL文を作成する
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
	    
	    	
	    	// ステートメントオブジェクトを作成する
	        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// プレースホルダーに値をセットする
	            ps.setString(1, id);
	            
	            // SQLを実行する
	            ResultSet rs = ps.executeQuery();
	            
	            // 結果をDTOに詰める
	            if (rs.next()) {
	            	CorporatorDto corporator = new CorporatorDto();
	            	corporator.setCorporatorId(rs.getInt("corporator_ID"));
	                return true;
	            }
	            // 該当するデータがない場合はfalseを返却する
	        	return false;
	        }
	    }
	
	
	public void insert(CorporatorDto dto) throws SQLException {
		
		//SQL文
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

		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	// プレースホルダーに値をセットする
            ps.setInt(1, dto.getCorporationId());
            ps.setString(2, dto.getEmail());
            ps.setString(3, dto.getPassword());
            ps.setString(4, dto.getFirstName());
            ps.setString(5, dto.getLastName());
            ps.setInt(6, dto.getUpdateOperatorId());
            
            // 実行
            ps.executeUpdate();
        }
		
	}
	
	
	public void update(CorporatorDto dto) throws SQLException {
		
		//SQL文成形
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

		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getFirstName());
            ps.setString(3, dto.getLastName());
            ps.setInt(4, dto.getUpdateOperatorId());
            ps.setInt(5, dto.getCorporatorId());
            
            // SQLを実行する
            ps.executeUpdate();
        }   
		
	}
	
	
	public void updateNotEmail(CorporatorDto dto) throws SQLException {
		
		//SQL文成形
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

		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setString(1, dto.getFirstName());
            ps.setString(2, dto.getLastName());
            ps.setInt(3, dto.getUpdateOperatorId());
            ps.setInt(4, dto.getCorporatorId());
            
            // SQLを実行する
            ps.executeUpdate();
        }   
		
	}
	
	
	
	public CorporatorDto findPasswordByuserIdPassword(int corporatorId,String oldPassword) throws SQLException {
		
		//SQL文成形
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

		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setInt(1, corporatorId);
            ps.setString(2, oldPassword);
            
            // SQLを実行する
            ResultSet rs = ps.executeQuery();
            
            // 結果をDTOに詰める
            if (rs.next()) {
            	CorporatorDto corporator = new CorporatorDto();
            	corporator.setCorporatorId(rs.getInt("CORPORATOR_ID"));
                return corporator;
            }
            // 該当するデータがない場合はnullを返却する
        	return null;
        }
	}
	
	public int updatePassword(CorporatorDto dto) throws SQLException {
		
		//SQL文の成形
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
		
		// ステイトメント作成
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// プレースホルダーに値をセットする
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getCorporatorId());
	      
	            //SQL文実行
	            return ps.executeUpdate();
		 }
		
	}
	
	
	public void resetPassword(CorporatorDto dto) throws SQLException {
		
		//SQL文生成
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
		
		
		// ステイトメント作成
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// プレースホルダーに値をセットする
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getUpdateOperatorId());
	            ps.setInt(3, dto.getCorporatorId());
	          
		 
	            //SQL文実行
	            ps.executeUpdate();
		 }

	}
	
	
	public List<Boolean> findResetPasswordById(List<CorporatorDto> dtoList) throws SQLException {

    	// SQL文を作成する
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
    	// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
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
		
		//SQL文成形
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("    WHERE");
		sb.append("        CORPORATOR_ID = ?;");


		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setInt(1, corporatorId);
            
            // SQLを実行する
            ps.executeUpdate();
        }   
		
	}
	
	
	public int deleteByCorporationId(int corporationId) throws SQLException {
		
		//SQL文成形
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE");
		sb.append("    FROM");
		sb.append("        CORPORATION_ACCOUNT");
		sb.append("    WHERE");
		sb.append("        CORPORATION_ID = ?;");


		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setInt(1, corporationId);
            
            // SQLを実行する
           return ps.executeUpdate();
        }   
		
	}
			
}
