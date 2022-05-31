package jp.motors.dto;


public class MessageDto {

	/* 連絡事項ID */
	private int messageId;
	
	/* 利用者ID */
	private int userId;
	
	/* 運用者ID */
	private int operatorId;

	/* 連絡時間 */
	private String messageAt;
	
	/* 連絡内容 */
	private String message;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getMessageAt() {
		return messageAt;
	}

	public void setMessageAt(String messageAt) {
		this.messageAt = messageAt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
