package jp.motors.dto;

import java.sql.Timestamp;

public class OperatorDto {

	/* ユーザID */
	private int operatorId;
	
	/* eメール */
	private String email;
	
	/* パスワード */
	private String password;

	/* 名 */
	private String firstName;

	/* 姓 */
	private String lastName;
	
	/* 権限 */
	private int authority;

	/* 更新ユーザID */
	private int updateOperatorId;
	
	/* 最終更新時刻 */
	private Timestamp updateAt;
	
	/* 更新番号 */
	private int updateNumber;
	
	/* 削除フラグ */
	private Boolean deleteFlg;
		
	/* メッセージ */
	private String message;
	
	/* メッセージ送信時間 */
	private String messageAt;
	
	/* 会社名 */
	private String corporationName;
	
	/* ユーザーID */
	private int userId;

	private boolean resetFlg;
	
	public int getUpdateOperatorId() {
		return updateOperatorId;
	}

	public void setUpdateOperatorId(int updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public int getUpdateNumber() {
		return updateNumber;
	}

	public void setUpdateNumber(int updateNumber) {
		this.updateNumber = updateNumber;
	}

	public Boolean getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessageAt() {
		return messageAt;
	}

	public void setMessageAt(String messageAt) {
		this.messageAt = messageAt;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isResetFlg() {
		return resetFlg;
	}

	public void setResetFlg(boolean resetFlg) {
		this.resetFlg = resetFlg;
	}
}
