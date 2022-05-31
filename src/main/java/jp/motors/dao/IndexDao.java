package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.CourseDto;
import jp.motors.dto.IndexDto;

public class IndexDao {

	/** コネクション */
	protected Connection conn;

	/**
	 * コンストラクタ
	 * コネクションをフィールドに設定する
	 * @param conn コネクション
	 */
	public IndexDao(Connection conn) {
        this.conn = conn;
    }
	
	
	
	
	/**
	 * 目次情報を取得する
	 * @return 目次情報リスト
	 * @throws SQLException SQL例外
	 */
	public List<IndexDto> selectByCourseId(int courseId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("         COURSE_ID");
		sb.append("        ,INDEX_ID");
		sb.append("        ,INDEXS");
		sb.append("        ,CONTENT");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("        ,DELETE_FLG");
		sb.append("    FROM");
		sb.append("         COURSE_DETAILS");
		sb.append("   WHERE");
		sb.append("         COURSE_ID = ?");
		sb.append("     AND DELETE_FLG = 0");
		sb.append("   ORDER BY INDEX_ID");


		List<IndexDto> list = new ArrayList<>();
    	// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setInt(1, courseId);
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				IndexDto dto = new IndexDto();
				dto.setCourseId(rs.getInt("COURSE_ID"));
				dto.setIndexId(rs.getInt("INDEX_ID"));
				dto.setIndexs(rs.getString("INDEXS"));
				dto.setContent(rs.getString("CONTENT"));
				dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
				dto.setDeleteFlg(rs.getBoolean("DELETE_FLG"));
				list.add(dto);
			}
			return list;
		}
	}
	

	/**
	 * 目次情報を取得する
	 * @return 目次情報リスト
	 * @throws SQLException SQL例外
	 */
	public IndexDto selectByIndexId(int indexId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("         COURSE_DETAILS.COURSE_ID");
		sb.append("        ,INDEX_ID");
		sb.append("        ,INDEXS");
		sb.append("        ,COURSE");
		sb.append("        ,CONTENT");
		sb.append("        ,COURSE_DETAILS.UPDATE_OPERATOR_ID");
		sb.append("        ,COURSE_DETAILS.UPDATE_AT");
		sb.append("        ,COURSE_DETAILS.UPDATE_NUMBER");
		sb.append("    FROM");
		sb.append("         COURSE_DETAILS INNER JOIN COURSE");
		sb.append("      ON COURSE_DETAILS.COURSE_ID = COURSE.COURSE_ID");
		sb.append("   WHERE");
		sb.append("         INDEX_ID = ?");


    	// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setInt(1, indexId);
			
			IndexDto dto = new IndexDto();
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setCourseId(rs.getInt("COURSE_DETAILS.COURSE_ID"));
				dto.setIndexId(rs.getInt("INDEX_ID"));
				dto.setIndexs(rs.getString("INDEXS"));
				dto.setCourse(rs.getString("COURSE"));
				dto.setContent(rs.getString("CONTENT"));
				dto.setUpdateOperatorId(rs.getInt("COURSE_DETAILS.UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("COURSE_DETAILS.UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("COURSE_DETAILS.UPDATE_NUMBER"));
			}
			return dto;
		}
	}
	
	/**
	 * freeコースフラグを取得する
	 * @return コース情報リスト
	 * @throws SQLException SQL例外
	 */
	public Boolean selectFreeCourseFlgByIndexId(int indexId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("    SELECT");
		sb.append("          FREE_COURSE_FLG");
		sb.append("     FROM");
		sb.append("          COURSE");
		sb.append("    RIGHT JOIN COURSE_DETAILS");
		sb.append("       ON COURSE_DETAILS.COURSE_ID = COURSE.COURSE_ID");
		sb.append("    WHERE");
		sb.append("          INDEX_ID = ?");


    	// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setInt(1, indexId);
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			Boolean freeCourseFlg = false;
			while (rs.next()) {
				freeCourseFlg = rs.getBoolean("FREE_COURSE_FLG");		
			}
			return freeCourseFlg;
		}
	}
	
	/**
	 *　最新の学習目次IDを取得する
	 * @return 目次情報リスト
	 * @throws SQLException SQL例外
	 */
	public int selectLastFinishedIndexId(int userId, int courseId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT");
		sb.append("         INDEX_ID");
		sb.append("    FROM");
		sb.append("         PROGRESS");
		sb.append("    WHERE");
		sb.append("         LECTURES_FINISH_AT = (");
		sb.append("            SELECT");
		sb.append("                    MAX(LECTURES_FINISH_AT)");
		sb.append("               FROM");
		sb.append("                    COURSE_DETAILS");
		sb.append("          LEFT JOIN PROGRESS");
		sb.append("                 ON PROGRESS.INDEX_ID = COURSE_DETAILS.INDEX_ID");
		sb.append("              WHERE");
		sb.append("                    USER_ID = ?");
		sb.append("                    AND COURSE_ID = ?");
		sb.append("        )");


    	// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setInt(1, userId);
			ps.setInt(2, courseId);
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int nextIndexId = rs.getInt("INDEX_ID");
				return nextIndexId;
			}
			return 0;
		}
	}



	/**
	 * テキストを作成する
	 * @param dto テキスト
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int insert(IndexDto dto) throws SQLException{
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into   COURSE_DETAILS");
		sb.append("           (");
		sb.append("               COURSE_ID");
		sb.append("              ,INDEXS");
		sb.append("              ,CONTENT");
		sb.append("              ,UPDATE_OPERATOR_ID");
		sb.append("              ,UPDATE_NUMBER");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("               ?");
		sb.append("              ,?");
		sb.append("              ,?");
		sb.append("              ,?");
		sb.append("              ,0");
		sb.append("           )");
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setInt(1, dto.getCourseId());
			ps.setString(2, dto.getIndexs());
			ps.setString(3, dto.getContent());
			ps.setInt(4, dto.getUpdateOperatorId());
			
			return ps.executeUpdate();
		}

	}




	public IndexDto checkIndexs(String indexs) throws SQLException{
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("   select");
		sb.append("          COURSE_ID");
		sb.append("         ,INDEXS");
		sb.append("         ,CONTENT");
		sb.append("         ,UPDATE_OPERATOR_ID");
		sb.append("         ,UPDATE_AT");
		sb.append("         ,UPDATE_NUMBER");
		sb.append("         ,DELETE_FLG");
		sb.append("     from COURSE_DETAILS");
		sb.append("    where INDEXS = ?");
		sb.append("      and DELETE_FLG = 0");
		
		IndexDto dto = new IndexDto();
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setString(1, indexs);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				dto.setCourseId(rs.getInt("COURSE_ID"));
				dto.setIndexs(rs.getString("INDEXS"));
				dto.setContent(rs.getString("CONTENT"));
				dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
				dto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
				dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));				
			}
		}
		return dto;		
		
	}




	public int update(IndexDto dto) throws SQLException{
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        COURSE_DETAILS");
		sb.append("    set");
		sb.append("        COURSE_ID = ?");
		sb.append("       ,INDEXS = ?");
		sb.append("       ,CONTENT = ?");
		sb.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("       ,UPDATE_AT = CURRENT_TIMESTAMP()");
		sb.append("  where INDEX_ID = ?");
		sb.append("    and UPDATE_NUMBER = ?");			
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setInt(1, dto.getCourseId());
			ps.setString(2, dto.getIndexs());
			ps.setString(3, dto.getContent());
			ps.setInt(4, dto.getIndexId());
			ps.setInt(5, dto.getUpdateNumber());
			
            // SQLを実行する
			return ps.executeUpdate();	
		}
		
	}




	public int deleteByIndexId(IndexDto dto) throws SQLException{
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        COURSE_DETAILS");
		sb.append("    SET");
		sb.append("        DELETE_FLG = 1");
		sb.append("    WHERE");
		sb.append("        INDEX_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
		// プレースホルダーに値をセットする
		ps.setInt(1, dto.getIndexId());

		// SQLを実行する
		return ps.executeUpdate();
		}		
		
	}




	public int deleteByCourseId(CourseDto dto) throws SQLException {
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        COURSE_DETAILS");
		sb.append("    SET");
		sb.append("        DELETE_FLG = 1");
		sb.append("    WHERE");
		sb.append("        COURSE_ID = ?;");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
		// プレースホルダーに値をセットする
		ps.setInt(1, dto.getCourseId());

		// SQLを実行する
		return ps.executeUpdate();
		}			
		
	}
}
