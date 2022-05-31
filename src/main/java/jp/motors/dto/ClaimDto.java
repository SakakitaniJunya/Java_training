package jp.motors.dto;


public class ClaimDto {

	/* 請求ID */
	private int claimId;
	
	/* 法人ID */
	private int corporationId;

	/* 請求年月 */
	private String claimYM;
	
	/* 利用者数 */
	private int numberOfUsers;
	
	/* 休止人数 */
	private int numberOfPause;
	
	private int expense;

	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public int getClaimId() {
		return claimId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public int getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(int corporationId) {
		this.corporationId = corporationId;
	}


	public String getClaimYM() {
		return claimYM;
	}

	public void setClaimYM(String claimYM) {
		this.claimYM = claimYM;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public int getNumberOfPause() {
		return numberOfPause;
	}

	public void setNumberOfPause(int numberOfPause) {
		this.numberOfPause = numberOfPause;
	}
}
