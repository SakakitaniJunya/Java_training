package jp.motors.dto;

import java.sql.Timestamp;

public class UserDto {

	/* ���[�UID */
	private int userId;
	
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
	
	/* �x�~�\��N���� */
	private Timestamp pauseAt;
	
	/* �폜�\��N���� */
	private Timestamp deleteAt;

	/* �X�V���[�UID */
	private int updateCorporatorId;
	
	/* �ŏI�X�V���� */
	private Timestamp updateAt;
	
	/* �X�V�ԍ� */
	private int updateNumber;
	
	/*�@�������t���O */
	private boolean ResetFlg;

	/* �J�E���g�p�@*/
	public static int count;
	
	public int getUpdateCorporatorId() {
		return updateCorporatorId;
	}

	public void setUpdateCorporatorId(int updateCorporatorId) {
		this.updateCorporatorId = updateCorporatorId;
	}

	public Timestamp getPauseAt() {
		return pauseAt;
	}

	public void setPauseAt(Timestamp pauseAt) {
		this.pauseAt = pauseAt;
	}

	public Timestamp getDeleteAt() {
		return deleteAt;
	}

	public void setDeleteAt(Timestamp deleteAt) {
		this.deleteAt = deleteAt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public boolean isResetFlg() {
		return ResetFlg;
	}

	public void setResetFlg(boolean resetFlg) {
		ResetFlg = resetFlg;
	}

}
