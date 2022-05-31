package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.CorporationDto;

public class CorporationDao {

	/** コネクション */
	protected Connection conn;

	/**
	 * コンストラクタ
	 * コネクションをフィールドに設定する
	 * @param conn コネクション
	 */
	public CorporationDao(Connection conn) {
        this.conn = conn;
    }
	
	public List<CorporationDto> selectAll() throws SQLException {
		
		//SQL文
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATION_ID");
		sb.append("        ,COMPANY_NAME");
		sb.append("        ,DOMAIN");
		sb.append("        ,BILLING_ADDRESS");
		sb.append("        ,START_AT");
		sb.append("        ,FINISH_AT");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("    FROM");
		sb.append("        CORPORATION;");

		//実行
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	//実行
        	ResultSet rs = ps.executeQuery();
        	
		//値をセット
        	List<CorporationDto> corporationList = new ArrayList<>();
        	while (rs.next()) {
        		CorporationDto dto = new CorporationDto();
        		dto.setCorporationId(rs.getInt("corporation_id"));
        		dto.setCompanyName(rs.getString("COMPANY_NAME"));
        		dto.setDomain(rs.getString("DOMAIN"));
        		dto.setBillingAddress(rs.getString("BILLING_ADDRESS"));
        		dto.setStartAt(rs.getTimestamp("START_AT"));
        		dto.setFinishAt(rs.getTimestamp("FINISH_AT"));
        		dto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
        		dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
        		dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
        		corporationList.add(dto);
        	}
        	return corporationList;
        }
		
		
	}
	
	//解約時刻が今と一致しているCorporationId
	public List<CorporationDto> selectByFinishAt() throws SQLException {
		//SQL文
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATION_ID");
		sb.append("        ,FINISH_AT");
		sb.append("    FROM");
		sb.append("        CORPORATION");
		sb.append("    WHERE");
		sb.append("        FINISH_AT <= TIMESTAMP(NOW());");

		// ステートメントオブジェクトを作成する
	       try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	           
	           // SQLを実行する
	           ResultSet rs = ps.executeQuery();
	         //値をセット
	        	List<CorporationDto> corporationList = new ArrayList<>();
	        	while (rs.next()) {
	        		CorporationDto dto = new CorporationDto();
	        		dto.setCorporationId(rs.getInt("CORPORATION_ID"));
	        		dto.setFinishAt(rs.getTimestamp("FINISH_AT"));
	        		corporationList.add(dto);
	        	}
	        	return corporationList;
	        }
	       
		
	}
	
	
	
	public void insert(CorporationDto dto) throws SQLException {
		
		//SQL文成形
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT");
		sb.append("    INTO");
		sb.append("        corporation(");
		sb.append("            Company_name");
		sb.append("            ,domain");
		sb.append("            ,billing_Address");
		sb.append("            ,update_operator_id");
		sb.append("            ,start_at");
		sb.append("            ,update_number");
		sb.append("        )");
		sb.append("    VALUES");
		sb.append("        (");
		sb.append("            ?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,0");
		sb.append("        );");

		// ステートメントオブジェクトを作成する
       try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
       	// プレースホルダーに値をセットする
           ps.setString(1, dto.getCompanyName());
           ps.setString(2, dto.getDomain());
           ps.setString(3, dto.getBillingAddress());
           ps.setInt(4, dto.getUpdateOperatorId());
           ps.setTimestamp(5, dto.getStartAt());
           
           // SQLを実行する
           ps.executeUpdate();
           
       }
	}

	public void updateCorporation(CorporationDto dto) throws SQLException {
		
		//SQL文成形
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        CORPORATION");
		sb.append("    SET");
		sb.append("        COMPANY_NAME = ?");
		sb.append("        ,DOMAIN = ?");
		sb.append("        ,BILLING_ADDRESS = ?");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("        ,UPDATE_OPERATOR_ID = ?");
		sb.append("        ,FINISH_AT = ?");
		sb.append("    WHERE");
		sb.append("        CORPORATION_ID = ?;");
	
		// ステートメントオブジェクトを作成する
	   try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	   	// プレースホルダーに値をセットする
	       ps.setString(1, dto.getCompanyName());
	       ps.setString(2, dto.getDomain());
	       ps.setString(3, dto.getBillingAddress());
	       ps.setInt(4, dto.getUpdateOperatorId());
	       ps.setTimestamp(5, dto.getFinishAt());
	       ps.setInt(6, dto.getCorporationId());
	       
	       // SQLを実行する
	       ps.executeUpdate();
	       
	   }
	}
	    

}
