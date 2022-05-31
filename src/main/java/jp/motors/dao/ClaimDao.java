package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.ClaimDto;
import jp.motors.dto.CorporationDto;

public class ClaimDao {

	/** コネクション */
	protected Connection conn;

	/**
	 * コンストラクタ
	 * コネクションをフィールドに設定する
	 * @param conn コネクション
	 */
	public ClaimDao(Connection conn) {
        this.conn = conn;
    }
	
	
	/**
	 * 請求情報を取得する
	 * @return 請求情報リスト
	 * @throws SQLException SQL例外
	 */
	public List<ClaimDto> selectAllByCorporationId(int corporationId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("   select");
		sb.append("          CLAIM_ID");
		sb.append("         ,CORPORATION_ID");
		sb.append("         ,CLAIM_YM");
		sb.append("         ,NUMBER_OF_USERS");
		sb.append("         ,NUMBER_OF_PAUSE");
		sb.append("     from CLAIM");
		sb.append("    where CORPORATION_ID = ?");
		sb.append(" order by CLAIM_YM DESC");

		List<ClaimDto> list = new ArrayList<>();
    	// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

			ps.setInt(1, corporationId);
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClaimDto dto = new ClaimDto();
				dto.setClaimId(rs.getInt("CLAIM_ID"));
				dto.setCorporationId(rs.getInt("CORPORATION_ID"));
				
				String time = rs.getTimestamp("CLAIM_YM").toString();
				dto.setClaimYM(time.substring(0, 10));
				
				dto.setNumberOfUsers(rs.getInt("NUMBER_OF_USERS"));
				dto.setNumberOfPause(rs.getInt("NUMBER_OF_PAUSE"));
				
				int price = 0;
				if (dto.getNumberOfUsers() <= 10) {
						price = 10000;
					} else if (dto.getNumberOfUsers() <= 100) {
						price = 8000;
					} else if (dto.getNumberOfUsers() <= 1000) {
						price = 6000;
					} else {
						price = 4000;
					}
				int expense = price * dto.getNumberOfUsers() - price / 2 * dto.getNumberOfPause();
				dto.setExpense(expense);
				
				list.add(dto);
			}
			return list;
		}
	}

	
	/**
	 * カテゴリを作成する
	 * @param dto カテゴリ
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int insert() throws SQLException {
		
		CorporationDao dao = new CorporationDao(conn);
		List<CorporationDto> dtoList = dao.selectAll();
		
		int count = 0;
		for (CorporationDto dto : dtoList) {
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT");
			sb.append("    INTO");
			sb.append("        CLAIM (");
			sb.append("             CORPORATION_ID");
			sb.append("            ,NUMBER_OF_USERS");
			sb.append("            ,NUMBER_OF_PAUSE");
			sb.append(") VALUES");
			sb.append("(?, ");
			sb.append("(SELECT COUNT(USER_ID) FROM USER WHERE (DELETE_AT > TIMESTAMP(NOW())  OR DELETE_AT IS NULL) and (PAUSE_AT > TIMESTAMP(NOW())  OR PAUSE_AT IS NULL)), ");
			sb.append("(SELECT COUNT(USER_ID) FROM USER WHERE PAUSE_AT < TIMESTAMP(NOW()))");
			sb.append(");");
			
			// ステートメントオブジェクトを作成する
			try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

				ps.setInt(1, dto.getCorporationId());
				// SQLを実行する
				ps.executeUpdate();
				count++;
			}
		}
		return count;
	}
	
	
	
}
