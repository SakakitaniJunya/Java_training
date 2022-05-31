package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.ProblemDto;
import jp.motors.dto.SelectionResultDto;

public class ProblemDao {

	/** コネクション */
	protected Connection conn;

	/**
	 * コンストラクタ
	 * コネクションをフィールドに設定する
	 * @param conn コネクション
	 */
	public ProblemDao(Connection conn) {
        this.conn = conn;
    }
	

	public List<ProblemDto> selectByCourseId(int courseId) throws SQLException{
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("     select");
		sb.append("            PROBLEM_ID");
		sb.append("           ,COURSE_ID");
		sb.append("           ,PROBLEM");
		sb.append("           ,PROBLEM_STATEMENT");
		sb.append("           ,CORRECT_SELECT_ID");
		sb.append("           ,UPDATE_OPERATOR_ID");
		sb.append("           ,UPDATE_AT");
		sb.append("           ,UPDATE_NUMBER");
		sb.append("       from PROBLEM");
		sb.append("      where COURSE_ID = ?");
		sb.append("   order by PROBLEM_ID");
		
		
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setInt(1, courseId);
			
			ResultSet rs = ps.executeQuery();
			
			List<ProblemDto> problemList = new ArrayList<>();
			
			while (rs.next()) {
				ProblemDto problemDto = new ProblemDto();
				problemDto.setProblemId(rs.getInt("PROBLEM_ID"));
				problemDto.setCourseId(rs.getInt("COURSE_ID"));
				problemDto.setProblem(rs.getString("PROBLEM"));
				problemDto.setProblemStatement(rs.getString("PROBLEM_STATEMENT"));
				problemDto.setCorrectSelectId(rs.getInt("CORRECT_SELECT_ID"));
				problemDto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
				problemDto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
				problemDto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
				
				SelectionResultDao selectionResultDao = new SelectionResultDao(conn);
				List<SelectionResultDto> selectionResultList = new ArrayList<>();
				selectionResultList = selectionResultDao.selectByProblemId(rs.getInt("PROBLEM_ID"));
				problemDto.setSelectionResultList(selectionResultList);
				
				problemList.add(problemDto);
			}
			
			return problemList;
		}
		
	}
	
	public int selectCorrectSelectionByProblemId(int problemId) throws SQLException{
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("     select");
		sb.append("            CORRECT_SELECT_ID");
		sb.append("       from PROBLEM");
		sb.append("      where PROBLEM_ID = ?");
		
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			ps.setInt(1, problemId);
			
			int correctSelectionId = 0;
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				correctSelectionId = rs.getInt("CORRECT_SELECT_ID");
			}
			return correctSelectionId;
		}
	}
	
	public int insert(ProblemDto problemDto) throws SQLException {
		// SQL文作成
		StringBuffer sb = new StringBuffer();
		sb.append("     INSERT");
		sb.append("       INTO");
		sb.append("        PROBLEM(");
		sb.append("             COURSE_ID");
		sb.append("            ,PROBLEM");
		sb.append("            ,PROBLEM_STATEMENT");
		sb.append("            ,CORRECT_SELECT_ID");
		sb.append("            ,UPDATE_OPERATOR_ID");
		sb.append("            ,UPDATE_NUMBER");
		sb.append("            )");
		sb.append("    VALUES");
		sb.append("            (");
		sb.append("             ?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,0");
		sb.append("            );");

		// preparedstatementオブジェクト作成
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			// プレースホルダに値をセットする
			ps.setInt(1, problemDto.getCourseId());
			ps.setString(2, problemDto.getProblem());
			ps.setString(3, problemDto.getProblemStatement());
			ps.setInt(4, problemDto.getCorrectSelectId());
			ps.setInt(5, problemDto.getUpdateOperatorId());
			
			return ps.executeUpdate();
			
		}
	}
	
	public int selectMaxProblemId() throws SQLException{
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("     select");
		sb.append("            MAX(PROBLEM_ID)");
		sb.append("       from PROBLEM");
		
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
			
			int maxProblemId = 0;
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				maxProblemId = rs.getInt("MAX(PROBLEM_ID)");
			}
			
			return maxProblemId;
		}
	}
	
	public int updateCorrectSelectionResultId(ProblemDto problemDto) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        PROBLEM");
		sb.append("    set");
		sb.append("        CORRECT_SELECT_ID = ?");
		sb.append("  where PROBLEM_ID = ?");
		
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setInt(1, problemDto.getCorrectSelectId());
			ps.setInt(2, problemDto.getProblemId());
			
			return ps.executeUpdate();
		}
		
	}
	
		public int update(ProblemDto problemDto) throws SQLException {
		
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE");
			sb.append("        PROBLEM");
			sb.append("    SET");
			sb.append("             PROBLEM = ?");
			sb.append("            ,PROBLEM_STATEMENT = ?");
			sb.append("            ,CORRECT_SELECT_ID = ?");
			sb.append("            ,UPDATE_OPERATOR_ID = ?");
			sb.append("            ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
			sb.append("    WHERE");
			sb.append("           PROBLEM_ID = ?");

			
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			ps.setString(1, problemDto.getProblem());
			ps.setString(2, problemDto.getProblemStatement());
			ps.setInt(3, problemDto.getCorrectSelectId());
			ps.setInt(4, problemDto.getUpdateOperatorId());
			ps.setInt(5, problemDto.getProblemId());
			
			return ps.executeUpdate();
		}
		
	}
		
		public int selectCountByCourseId(int courseId) throws SQLException{
			// SQL文を作成する
			StringBuffer sb = new StringBuffer();
			sb.append("     select");
			sb.append("            COUNT(PROBLEM_ID)");
			sb.append("       from PROBLEM");
			sb.append("      where COURSE_ID = ?");
			
			
			try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
				
				ps.setInt(1, courseId);
				
				int count = 0;
				
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					count = rs.getInt("COUNT(PROBLEM_ID)");
				}
				return count;
			}
		}
}
