package jp.motors.dto;

import java.sql.Timestamp;

public class UserDto {

	/* ユーザID */
	private int userId;
	
	/* 法人ID */
	private int corporationId;
	
	/* eメール */
	private String email;
	
	/* パスワード */
	private String password;

	/* 名 */
	private String firstName;

	/* 姓 */
	private String lastName;
	
	/* 休止予定年月日 */
	private Timestamp pauseAt;
	
	/* 削除予定年月日 */
	private Timestamp deleteAt;

	/* 更新ユーザID */
	private int updateCorporatorId;
	
	/* 最終更新時刻 */
	private Timestamp updateAt;
	
	/* 更新番号 */
	private int updateNumber;
	
	/*　初期化フラグ */
	private boolean ResetFlg;

	/* カウント用　*/
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
