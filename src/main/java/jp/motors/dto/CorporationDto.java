package jp.motors.dto;

import java.sql.Timestamp;

public class CorporationDto {

	/* �@�lID */
	private int corporationId;
	
	/* ��Ж� */
	private String companyName;
	
	/* �h���C���� */
	private String domain;
	
	/* ������ */
	private String billingAddress;

	/* �_��J�n�\��N���� */
	private Timestamp startAt;
	
	/* �_��I���\��N���� */
	private Timestamp finishAt;

	/* �X�V���[�UID */
	private int updateOperatorId;

	/* �ŏI�X�V���� */
	private Timestamp updateAt;
	
	/* �X�V�ԍ� */
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
