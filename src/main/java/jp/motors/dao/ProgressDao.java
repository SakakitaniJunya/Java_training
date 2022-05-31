package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.ProgressDto;

public class ProgressDao {


	protected Connection conn;

	
	public ProgressDao(Connection conn) {
        this.conn = conn;
    }
	
	public List<ProgressDto> selectByUserId(int userId) throws SQLException{
		//
		StringBuffer sb = new StringBuffer();
		sb.append("     SELECT PROGRESS_ID");
		sb.append("           ,USER_ID");
		sb.append("           ,INDEX_ID");
		sb.append("           ,LECTURES_FINISH_AT");
		sb.append("       FROM");
		sb.append("            PROGRESS");
		sb.append("      WHERE USER_ID =?");
	
		
		List<ProgressDto> list = new ArrayList<>();
	
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProgressDto dto = new ProgressDto();
				dto.setIndexId(rs.getInt("INDEX_ID"));
				dto.setUserId(rs.getInt("USER_ID"));
				dto.setProgressId(rs.getInt("PROGRESS_ID"));
				dto.setLecturesFinishAt(rs.getTimestamp("LECTURES_FINISH_AT"));
				list.add(dto);
			}
		}
		return list;
	}
	

	public int insert(ProgressDto dto) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO PROGRESS(");
		sb.append("            USER_ID");
		sb.append("           ,INDEX_ID");
		sb.append("           )");
		sb.append("     VALUES");
		sb.append("           (");
		sb.append("             ?");
		sb.append("            ,?");
		sb.append("           )");

		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
		
			ps.setInt(1, dto.getUserId());
			ps.setInt(2, dto.getIndexId());
			

			return ps.executeUpdate();
		}
		
	}

	


	public List<ProgressDto> selectByCorporationId(int corporationId) throws SQLException{
		
	
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT");
		sb.append("         PROGRESS_ID");
		sb.append("        ,PROGRESS.USER_ID");
		sb.append("        ,PROGRESS.INDEX_ID");
		sb.append("        ,LECTURES_FINISH_AT");
		sb.append("        ,LAST_NAME");
		sb.append("        ,FIRST_NAME");
		sb.append("        ,CATEGORY");
		sb.append("        ,COURSE");
		sb.append("        ,INDEXS");
		sb.append("    FROM");
		sb.append("         PROGRESS INNER JOIN USER");
		sb.append("      ON PROGRESS.USER_ID = USER.USER_ID INNER JOIN COURSE_DETAILS");
		sb.append("      ON PROGRESS.INDEX_ID = COURSE_DETAILS.INDEX_ID INNER JOIN COURSE");
		sb.append("      ON COURSE_DETAILS.COURSE_ID = COURSE.COURSE_ID INNER JOIN CATEGORY");
		sb.append("      ON COURSE.CATEGORY_ID = CATEGORY.CATEGORY_ID");
		sb.append("   WHERE");
		sb.append("         CORPORATION_ID = ? ");
		sb.append("ORDER BY LECTURES_FINISH_AT DESC");

		List<ProgressDto> list = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setInt(1, corporationId);
			
		
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProgressDto dto = new ProgressDto();
				dto.setProgressId(rs.getInt("PROGRESS_ID"));
				dto.setUserId(rs.getInt("PROGRESS.USER_ID"));
				dto.setIndexId(rs.getInt("PROGRESS.INDEX_ID"));
				dto.setLecturesFinishAt(rs.getTimestamp("LECTURES_FINISH_AT"));
				dto.setLastName(rs.getString("LAST_NAME"));
				dto.setFirstName(rs.getString("FIRST_NAME"));
				dto.setCategory(rs.getString("CATEGORY"));
				dto.setCourse(rs.getString("COURSE"));
				dto.setIndexs(rs.getString("INDEXS"));
				list.add(dto);
			}
			return list;
		}

	}

	
	
	public List<ProgressDto> selectByQueries(int corporationId, String[] progressList) throws SQLException {

		//SQLï∂çÏê¨
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("            PROGRESS_ID");
		sb.append("            ,PROGRESS.USER_ID");
		sb.append("            ,PROGRESS.INDEX_ID");
		sb.append("            ,PROGRESS.LECTURES_FINISH_AT");
		sb.append("            ,LAST_NAME");
		sb.append("            ,FIRST_NAME");
		sb.append("            ,CATEGORY");
		sb.append("            ,COURSE");
		sb.append("            ,INDEXS");
		sb.append("        FROM");
		sb.append("            PROGRESS INNER JOIN USER");
		sb.append("                ON PROGRESS.USER_ID = USER.USER_ID INNER JOIN COURSE_DETAILS");
		sb.append("                ON PROGRESS.INDEX_ID = COURSE_DETAILS.INDEX_ID INNER JOIN COURSE");
		sb.append("                ON COURSE_DETAILS.COURSE_ID = COURSE.COURSE_ID INNER JOIN CATEGORY");
		sb.append("                ON COURSE.CATEGORY_ID = CATEGORY.CATEGORY_ID");
		sb.append("    WHERE");
		//sb.append("         CORPORATION_ID = ? ");
		sb.append("        (");
		sb.append("            LAST_NAME LIKE ?");
		sb.append("            OR FIRST_NAME LIKE ?");
		sb.append("            OR CATEGORY.CATEGORY LIKE ?");
		sb.append("            OR COURSE.COURSE LIKE ?");
		sb.append("            OR INDEXS LIKE ?");
		sb.append("        )");
		for(int i = 1; i < progressList.length; i++) {
		
		sb.append("        AND (");
		sb.append("            LAST_NAME LIKE ?");
		sb.append("            OR FIRST_NAME LIKE ?");
		sb.append("            OR CATEGORY.CATEGORY LIKE ?");
		sb.append("            OR COURSE.COURSE LIKE ?");
		sb.append("            OR INDEXS LIKE ?");
		sb.append("        )");
		}
		sb.append("    ORDER BY");
		sb.append("        LECTURES_FINISH_AT DESC;");

		
		List<ProgressDto> list = new ArrayList<>();
		

			try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
				//ps.setInt(1, corporationId);
				for(int i = 0; i < progressList.length; i++) {
					ps.setString(5*i + 1, "%" + progressList[i] + "%" );
					ps.setString(5*i + 2, "%" + progressList[i] + "%" );
					ps.setString(5*i + 3, "%" + progressList[i] + "%" );
					ps.setString(5*i + 4, "%" + progressList[i] + "%" );
					ps.setString(5*i + 5, "%" + progressList[i] + "%" );
				}
			
				
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProgressDto dto = new ProgressDto();
				dto.setProgressId(rs.getInt("progress_id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setIndexId(rs.getInt("index_id"));
				dto.setFirstName(rs.getString("first_name"));;
				dto.setLastName(rs.getString("last_name"));
				dto.setCategory(rs.getString("Category"));
				dto.setCourse(rs.getString("Course"));
				dto.setIndexs(rs.getString("indexs"));
				dto.setLecturesFinishAt(rs.getTimestamp("LECTURES_FINISH_AT"));
				list.add(dto);
			}
			return list;
		}
	}
	
}






