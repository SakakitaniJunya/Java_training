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

	/** コネクション */
	protected Connection conn;

	/**
	 * コンストラクタ
	 * コネクションをフィールドに設定する
	 * @param conn コネクション
	 */
	public CategoryDao(Connection conn) {
        this.conn = conn;
    }
	
	
	
	
	/**
	 * カテゴリを作成する
	 * @param dto カテゴリ
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int insert(CategoryDto dto) throws SQLException {
    	// SQL文を作成する
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
		
    	// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, dto.getCategory());
			ps.setInt(2, dto.getUpdateOperatorId());

			// SQLを実行する
			return ps.executeUpdate();
		}		
		
	}
	
	/**
	 * カテゴリ情報を取得する
	 * @return カテゴリ情報リスト
	 * @throws SQLException SQL例外
	 */
	public List<CategoryDto> selectAll() throws SQLException {

		// SQL文を作成する
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
    	// ステートメントオブジェクトを作成する
		try (Statement stmt = conn.createStatement()) {

			// SQL文を実行する
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
	 * カテゴリ情報を取得する
	 * @param categoryId　カテゴリID
	 * @return カテゴリ情報
	 * @throws SQLException SQL例外
	 */
	public CategoryDto selectByCategoryId(int categoryId) throws SQLException {
		// SQL文を作成する
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
	 * カテゴリ情報を更新する
	 * @param dto カテゴリ情報
	 * @return 更新件数
	 * @throws SQLException SQL例外
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
		// SQL文を作成する
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
