package jp.motors.dto;


public class MessageDto {

	/* �A������ID */
	private int messageId;
	
	/* ���p��ID */
	private int userId;
	
	/* �^�p��ID */
	private int operatorId;

	/* �A������ */
	private String messageAt;
	
	/* �A�����e */
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
