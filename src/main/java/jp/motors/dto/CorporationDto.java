package jp.motors.dto;

import java.sql.Timestamp;

public class CorporationDto {

	/* 法人ID */
	private int corporationId;
	
	/* 会社名 */
	private String companyName;
	
	/* ドメイン名 */
	private String domain;
	
	/* 請求先 */
	private String billingAddress;

	/* 契約開始予定年月日 */
	private Timestamp startAt;
	
	/* 契約終了予定年月日 */
	private Timestamp finishAt;

	/* 更新ユーザID */
	private int updateOperatorId;

	/* 最終更新時刻 */
	private Timestamp updateAt;
	
	/* 更新番号 */
	private int updateNumber;
	
	public int getUpdateOperatorId() {
		return updateOperatorId;
	}

	public void setUpdateOperatorId(int updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public int getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(int corporationId) {
		this.corporationId = corporationId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Timestamp getStartAt() {
		return startAt;
	}

	public void setStartAt(Timestamp startAt) {
		this.startAt = startAt;
	}

	public Timestamp getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(Timestamp finishAt) {
		this.finishAt = finishAt;
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
