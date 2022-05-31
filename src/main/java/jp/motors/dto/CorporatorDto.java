package jp.motors.dto;

import java.sql.Timestamp;

public class CorporatorDto {

	/* ���[�UID */
	private int corporatorId;
	
	/* �@�lID */
	private int corporationId;
	
	/* e���[�� */
	private String email;
	
	/* �p�X���[�h */
	private String password;

	/* �� */
	private String firstName;

	/* �� */
	private String lastName;

	/* �X�V���[�UID */
	private int updateOperatorId;
	
	/* �ŏI�X�V���� */
	private Timestamp updateAt;
	
	/* �X�V�ԍ� */
	private int updateNumber;
	
	/* �������t���O */
	private boolean resetFlg;

	public boolean isResetFlg() {
		return resetFlg;
	}

	public void setResetFlg(boolean resetFlg) {
		this.resetFlg = resetFlg;
	}

	public int getUpdateOperatorId() {
		return updateOperatorId;
	}

	public void setUpdateOperatorId(int updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public int getCorporatorId() {
		return corporatorId;
	}

	public void setCorporatorId(int corporatorId) {
		this.corporatorId = corporatorId;
	}

	public int getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(int corporationId) {
		this.corporationId = corporationId;
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

}
