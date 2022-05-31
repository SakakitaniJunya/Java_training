package jp.motors.dto;

import java.sql.Timestamp;

public class OperatorDto {

	/* ���[�UID */
	private int operatorId;
	
	/* e���[�� */
	private String email;
	
	/* �p�X���[�h */
	private String password;

	/* �� */
	private String firstName;

	/* �� */
	private String lastName;
	
	/* ���� */
	private int authority;

	/* �X�V���[�UID */
	private int updateOperatorId;
	
	/* �ŏI�X�V���� */
	private Timestamp updateAt;
	
	/* �X�V�ԍ� */
	private int updateNumber;
	
	/* �폜�t���O */
	private Boolean deleteFlg;
		
	/* ���b�Z�[�W */
	private String message;
	
	/* ���b�Z�[�W���M���� */
	private String messageAt;
	
	/* ��Ж� */
	private String corporationName;
	
	/* ���[�U�[ID */
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
