package jp.motors.dto;

import java.sql.Timestamp;

public class CategoryDto {
	
	/* �J�e�S��ID */
	private int categoryId;
	
	/* �J�e�S���� */
	private String category;
	
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
