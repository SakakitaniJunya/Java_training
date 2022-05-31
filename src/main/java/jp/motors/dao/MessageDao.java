package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.MessageDto;
import jp.motors.dto.OperatorDto;
import jp.motors.dto.UserDto;

public class MessageDao {

	/** 繧ｳ繝阪け繧ｷ繝ｧ繝ｳ */
	protected Connection conn;

	/**
	 * 繧ｳ繝ｳ繧ｹ繝医Λ繧ｯ繧ｿ
	 * 繧ｳ繝阪け繧ｷ繝ｧ繝ｳ繧偵ヵ繧｣繝ｼ繝ｫ繝峨↓險ｭ螳壹☆繧�
	 * @param conn 繧ｳ繝阪け繧ｷ繝ｧ繝ｳ
	 */
	public MessageDao(Connection conn) {
        this.conn = conn;
    }
	
	//繝ｦ繝ｼ繧ｶ繝ｼ繝｡繝�繧ｻ繝ｼ繧ｸ�ｼ�userId縺九ｉ�ｼ�
	public List<MessageDto> selectMessageByUserId(int userId) throws SQLException {
	
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        USER_ID");
		sb.append("        ,MESSAGE_ID");
		sb.append("        ,OPERATOR_ID");
		sb.append("        ,MESSAGE");
		sb.append("        ,MESSAGE_AT");
		sb.append("    FROM");
		sb.append("        message");
		sb.append("    WHERE");
		sb.append("        user_id = ?");
		sb.append("    ORDER BY");
		sb.append("        MESSAGE_AT DESC;");
		
		List<MessageDto> messageList = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {  
			// SQL繧貞ｮ溯｡後☆繧�
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MessageDto dto = new MessageDto();
				// 繝翫Ξ繝�繧ｸ諠�蝣ｱ縲√メ繝｣繝ｳ繝阪ΝID繧偵そ繝�繝医☆繧�
				dto.setMessageId(rs.getInt("message_id"));
				dto.setMessage(rs.getString("message"));
				dto.setOperatorId(rs.getInt("Operator_id"));
				dto.setUserId(rs.getInt("user_id"));	
				String time = rs.getTimestamp("message_at").toString();
				dto.setMessageAt(time.substring(0, 16));
				messageList.add(dto);
			}
		return messageList;
		}

	}
	
	public void insertMessageByUserId(MessageDto dto) throws SQLException {
		
		//SQL謌仙ｽ｢
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT");
		sb.append("    INTO");
		sb.append("        message(");
		sb.append("            user_id");
		sb.append("            ,operator_id");
		sb.append("            ,message");
		sb.append("        )");
		sb.append("    VALUES");
		sb.append("        (");
		sb.append("            ?");
		sb.append("            ,null");
		sb.append("            ,?");
		sb.append("        );");

		//繧ｹ繝�繧､繝医Γ繝ｳ繝井ｽ懈��
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	// 繝励Ξ繝ｼ繧ｹ繝帙Ν繝�繝ｼ繧偵そ繝�繝医☆繧�
			ps.setInt(1, dto.getUserId());
			ps.setString(2, dto.getMessage());
			
            // SQL螳溯｡�
			ps.executeUpdate();
		}
	}
	
	public void insertMessageByUserIdAndOperatorId(MessageDto dto) throws SQLException {
			
			//SQL謌仙ｽ｢
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT");
			sb.append("    INTO");
			sb.append("        message(");
			sb.append("            user_id");
			sb.append("            ,operator_id");
			sb.append("            ,message");
			sb.append("        )");
			sb.append("    VALUES");
			sb.append("        (");
			sb.append("            ?");
			sb.append("            ,?");
			sb.append("            ,?");
			sb.append("        );");
	
			//繧ｹ繝�繧､繝医Γ繝ｳ繝井ｽ懈��
			try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	        	// 繝励Ξ繝ｼ繧ｹ繝帙Ν繝�繝ｼ繧偵そ繝�繝医☆繧�
				ps.setInt(1, dto.getUserId());
				ps.setInt(2, dto.getOperatorId());
				ps.setString(3, dto.getMessage());
				
	            // SQL螳溯｡�
				ps.executeUpdate();
			}
		}
	
	
	public List<OperatorDto> selectMessage() throws SQLException {
		
		
		//SQL文形成
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        email");
		sb.append("        ,first_name");
		sb.append("        ,last_name");
		sb.append("        ,company_name");
		sb.append("        ,message");
		sb.append("        ,message_at");
		sb.append("        ,user.user_id");
		sb.append("    FROM");
		sb.append("        (");
		sb.append("            message INNER JOIN USER");
		sb.append("                ON message.user_id = user.user_id");
		sb.append("        ) INNER JOIN corporation");
		sb.append("        ON user.corporation_id = corporation.corporation_id");
		sb.append(" 	ORDER BY");
		sb.append("		message_at DESC;");
		
		// ステイトメント作成
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
		
			 //実行
			ResultSet rs = ps.executeQuery();
			
			List<OperatorDto> operatorList = new ArrayList<>();
			
			
			 while (rs.next()) {
				 	OperatorDto dto = new OperatorDto();
				 	dto.setEmail(rs.getString("email"));
					dto.setFirstName(rs.getString("first_name"));
					dto.setLastName(rs.getString("last_name"));
					dto.setCorporationName(rs.getString("company_name"));
					dto.setMessage(rs.getString("message"));
					String time = rs.getTimestamp("message_at").toString();
					dto.setMessageAt(time.substring(0, 16));
					dto.setUserId(rs.getInt("user_id"));
					operatorList.add(dto);
			}
			return operatorList;
		}
			 
	}
	
	
	public List<OperatorDto> selectMessageByNotSend(List<UserDto> userList) throws SQLException {
		
		//SQL文形成
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        email");
		sb.append("        ,first_name");
		sb.append("        ,last_name");
		sb.append("        ,company_name");
		sb.append("        ,message");
		sb.append("        ,message_at");
		sb.append("        ,user.user_id");
		sb.append("    FROM");
		sb.append("        (");
		sb.append("            message INNER JOIN USER");
		sb.append("                ON message.user_id = user.user_id");
		sb.append("        ) INNER JOIN corporation");
		sb.append("        ON user.corporation_id = corporation.corporation_id");
		sb.append("	WHERE");
		sb.append("         (");
		sb.append("         message_at = (");
		sb.append("            SELECT");
		sb.append("                    MAX(message_at)");
		sb.append("                FROM");
		sb.append("                    message");
		sb.append("                where ");
		sb.append("                	user_id = ");
		sb.append("                	? )");		
		for (int i = 1;i < userList.size(); i++) {
			sb.append("        or  message_at =  (");
			sb.append("            SELECT");
			sb.append("                    MAX(message_at)");
			sb.append("                FROM");
			sb.append("                    message");
			sb.append("                where ");
			sb.append("                	user_id = ");
			sb.append("                	?");
			sb.append("        ) ");
		}
		sb.append("     ) and  operator_id IS null");
		sb.append(" 	ORDER BY");
		sb.append("		message_at ASC;");
		
		// ステイトメント作成
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			int count = 0;
			// プレースホルダーに値をセットする
			for(UserDto userDto: userList) {
				count++;
				ps.setInt(count, userDto.getUserId());
			}
			 //実行
			ResultSet rs = ps.executeQuery();
			
			List<OperatorDto> operatorList = new ArrayList<>();
			
			
			 while (rs.next()) {
				 	OperatorDto dto = new OperatorDto();
				 	dto.setEmail(rs.getString("email"));
					dto.setFirstName(rs.getString("first_name"));
					dto.setLastName(rs.getString("last_name"));
					dto.setCorporationName(rs.getString("company_name"));
					dto.setMessage(rs.getString("message"));
					String time = rs.getTimestamp("message_at").toString();
					dto.setMessageAt(time.substring(0, 16));
					dto.setUserId(rs.getInt("user_id"));
					operatorList.add(dto);
			}
			return operatorList;
		}
			 
	}
	
	public List<OperatorDto> selectMessageBySend(List<UserDto> userList) throws SQLException {
		
		//SQL文形成
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        email");
		sb.append("        ,first_name");
		sb.append("        ,last_name");
		sb.append("        ,company_name");
		sb.append("        ,message");
		sb.append("        ,message_at");
		sb.append("        ,user.user_id");
		sb.append("    FROM");
		sb.append("        (");
		sb.append("            message INNER JOIN USER");
		sb.append("                ON message.user_id = user.user_id");
		sb.append("        ) INNER JOIN corporation");
		sb.append("        ON user.corporation_id = corporation.corporation_id");
		sb.append("	WHERE ");
		sb.append("       (  message_at = (");
		sb.append("            SELECT");
		sb.append("                    MAX(message_at)");
		sb.append("                FROM");
		sb.append("                    message");
		sb.append("                where ");
		sb.append("                	user_id = ");
		sb.append("                	? 	) and operator_id is not null )");		
		for (int i = 1;i < userList.size(); i++) {
			sb.append("         or ");
			sb.append("       (  message_at = (");
			sb.append("            SELECT");
			sb.append("                    MAX(message_at)");
			sb.append("                FROM");
			sb.append("                    message");
			sb.append("                where ");
			sb.append("                	user_id = ");
			sb.append("                	? 	) and operator_id is not null )");		
		}
		sb.append("	 ORDER BY");
		sb.append("		message_at DESC;");
		
		// ステイトメント作成
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			int count = 0;
			// プレースホルダーに値をセットする
			for(UserDto userDto: userList) {
				count++;
				ps.setInt(count, userDto.getUserId());
			}
			 //実行
			ResultSet rs = ps.executeQuery();
			
			List<OperatorDto> operatorList = new ArrayList<>();
			
			 while (rs.next()) {
				    OperatorDto dto = new OperatorDto();
					dto.setEmail(rs.getString("email"));
					dto.setFirstName(rs.getString("first_name"));
					dto.setLastName(rs.getString("last_name"));
					dto.setCorporationName(rs.getString("company_name"));
					dto.setMessage(rs.getString("message"));
					String time = rs.getTimestamp("message_at").toString();
					dto.setMessageAt(time.substring(0, 16));
					dto.setUserId(rs.getInt("user_id"));
					operatorList.add(dto);
			}
			return operatorList;
		}
	}
	
	
	//　運用者の最新の返信
	public List<OperatorDto> selectMessageByOldSend(List<OperatorDto> userList) throws SQLException {
		
		//SQL文形成
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        email");
		sb.append("        ,first_name");
		sb.append("        ,last_name");
		sb.append("        ,company_name");
		sb.append("        ,message");
		sb.append("        ,message_at");
		sb.append("        ,user.user_id");
		sb.append("    FROM");
		sb.append("        (");
		sb.append("            message INNER JOIN USER");
		sb.append("                ON message.user_id = user.user_id");
		sb.append("        ) INNER JOIN corporation");
		sb.append("        ON user.corporation_id = corporation.corporation_id");
		sb.append("	WHERE not ");
		sb.append("       (  message_at = (");
		sb.append("            SELECT");
		sb.append("                    MAX(message_at)");
		sb.append("                FROM");
		sb.append("                    message");
		sb.append("                where ");
		sb.append("                	user_id = ");
		sb.append("                	? 	) and operator_id is not null )");		
		for (int i = 1;i < userList.size(); i++) {
			sb.append("         or ");
			sb.append("       (  message_at = (");
			sb.append("            SELECT");
			sb.append("                    MAX(message_at)");
			sb.append("                FROM");
			sb.append("                    message");
			sb.append("                where ");
			sb.append("                	user_id = ");
			sb.append("                	? 	) and operator_id is not null )");		
		}
		sb.append("	 ORDER BY");
		sb.append("		message_at ASC;");
		
		// ステイトメント作成
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			
			int count = 0;
			// プレースホルダーに値をセットする
			for(OperatorDto userDto: userList) {
				count++;
				ps.setInt(count, userDto.getUserId());
			}
			 //実行
			ResultSet rs = ps.executeQuery();
			
			List<OperatorDto> operatorList = new ArrayList<>();
			
			 while (rs.next()) {
				    OperatorDto dto = new OperatorDto();
					dto.setEmail(rs.getString("email"));
					dto.setFirstName(rs.getString("first_name"));
					dto.setLastName(rs.getString("last_name"));
					dto.setCorporationName(rs.getString("company_name"));
					dto.setMessage(rs.getString("message"));

					String time = rs.getTimestamp("message_at").toString();
					dto.setMessageAt(time.substring(0, 16));
					
					dto.setUserId(rs.getInt("user_id"));
					operatorList.add(dto);
			}
			return operatorList;
		}
	}
			 
	
	
	


}
