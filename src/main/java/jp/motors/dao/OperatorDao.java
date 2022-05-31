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
	 * コンストラクタ
	 * コネクションをフィールドに設定する
	 * @param conn コネクション
	 */
	public OperatorDao(Connection conn) {
        this.conn = conn;
    }
	
	
	public List<OperatorDto> selectAll() throws SQLException {
		
		//SQL文
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

		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	//実行
        	ResultSet rs = ps.executeQuery();
        	
        	// 結果をListに詰める
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
		
		//SQL文
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

		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	// ステイトメントセット
            ps.setInt(1, operatorId);
        	
        	//実行
        	ResultSet rs = ps.executeQuery();
        	
        	// 結果をListに詰める
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
     * ユーザ情報を取得する
     * @param id ID
     * @param password パスワード
     * @return ユーザ情報
     * @throws SQLException SQL例外
     */
	public OperatorDto findByIdAndPassword(String id, String password) throws SQLException {
		// SQL文を作成する
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
    	
		
    	// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setString(1, id);
            ps.setString(2, password);
            
            // SQLを実行する
            ResultSet rs = ps.executeQuery();
            
            // 結果をDTOに詰める
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
            // 該当するデータがない場合はnullを返却する
        	return null;
        }
	}
	
	public List<Boolean> findResetPasswordById(List<OperatorDto> dtoList) throws SQLException {

    	// SQL文を作成する
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
    	
    	// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
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
	
	//IDとパスワードの入力チェック
	public OperatorDto findPasswordByuserIdPassword(int OperatorId,String oldPassword) throws SQLException {
		
		//SQL文成形
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


		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setInt(1, OperatorId);
            ps.setString(2, oldPassword);
            
            // SQLを実行する
            ResultSet rs = ps.executeQuery();
            
            // 結果をDTOに詰める
            if (rs.next()) {
            	OperatorDto operator = new OperatorDto();
            	operator.setOperatorId(rs.getInt("OPERATOR_ID"));
                return operator;
            }
            // 該当するデータがない場合はnullを返却する
        	return null;
        }
	}
	

	
	
	
	
	
			 
		public void insert(OperatorDto dto) throws SQLException {
			
			//SQL文成形
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

			// ステートメントオブジェクトを作成する
	        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// プレースホルダーに値をセットする
	            ps.setString(1, dto.getEmail());
	            ps.setString(2, dto.getPassword());
	            ps.setString(3, dto.getFirstName());
	            ps.setString(4, dto.getLastName());
	            ps.setInt(5, dto.getAuthority());
	            ps.setInt(6, dto.getUpdateOperatorId());
	            
	            
	            // SQLを実行する
	            ps.executeUpdate();
	            
		}
	}

		
	public void updateOperator(OperatorDto dto) throws SQLException {
		
		//SQL文成形
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
		
		
		// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getFirstName());
            ps.setString(3, dto.getLastName());
            ps.setInt(4, dto.getAuthority());
            ps.setInt(5, dto.getUpdateOperatorId());
            ps.setInt(6, dto.getOperatorId());
            
            
            // SQLを実行する
            ps.executeUpdate();
        }   
		
	}
	
	public int updatePassword(OperatorDto dto) throws SQLException {
		
		//SQL文の成形
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

		
		// ステイトメント作成
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// プレースホルダーに値をセットする
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getOperatorId());
	      
	            //SQL文実行
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
		
		

		// ステイトメント作成
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			 
			 //プレースホルダーにセット
			 ps.setInt(1,  dto.getUpdateOperatorId());
			 ps.setInt(2, dto.getOperatorId());
			 
			 //SQL文実行
			 ps.executeUpdate();
		 }
		
	}
	
    public boolean resetCheck(String id, String password) throws SQLException {

    	// SQL文を作成する
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
    
    	
    	// ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
            ps.setString(1, id);
            
            // SQLを実行する
            ResultSet rs = ps.executeQuery();
            
            // 結果をDTOに詰める
            if (rs.next()) {
                OperatorDto operator = new OperatorDto();
                operator.setOperatorId(rs.getInt("Operator_ID"));
                return true;
            }
            // 該当するデータがない場合はfalseを返却する
        	return false;
        }
    }
    
	
	
	public void resetPassword(OperatorDto dto) throws SQLException {
		
		//SQL文生成
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
		
		
		// ステイトメント作成
		 try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// プレースホルダーに値をセットする
	            ps.setString(1, dto.getPassword());
	            ps.setInt(2, dto.getUpdateOperatorId());
	            ps.setInt(3, dto.getOperatorId());
	          
		 
	            //SQL文実行
	            ps.executeUpdate();
		 }

	}
}
