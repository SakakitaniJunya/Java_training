package jp.motors.dto;

import java.sql.Timestamp;

public class IndexDto {

	/* �ڎ�ID */
	private int indexId;
	
	/*�@���p��ID */
	private int userId;
	
	/* �i��ID */
	private int progressId;
	
	/* ��u�������� */
	private Timestamp lecturesFinishAt;
	
	/* �R�[�XID */
	private int courseId;
	
	/* �R�[�X�� */
	private String course;
	
	/* �ڎ��� */
	private String indexs;
	
	/* �e�L�X�g */
	private String content;
	
	/* �X�V���[�UID */
	private int updateOperatorId;
	
	/* �ŏI�X�V���� */
	private Timestamp updateAt;
	
	/* �X�V�ԍ� */
	private int updateNumber;
	
	private Boolean deleteFlg;
	
	public Boolean getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	private Boolean lecturesFinished;

	public Boolean getLecturesFinished() {
		return lecturesFinished;
	}

	public void setLecturesFinished(Boolean lecturesFinished) {
		this.lecturesFinished = lecturesFinished;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getProgressId() {
		return progressId;
	}

	public void setProgressId(int progressId) {
		this.progressId = progressId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getLecturesFinishAt() {
		return lecturesFinishAt;
	}

	public void setLecturesFinishAt(Timestamp lecturesFinishAt) {
		this.lecturesFinishAt = lecturesFinishAt;
	}

	public String getIndexs() {
		return indexs;
	}

	public void setIndexs(String indexs) {
		this.indexs = indexs;
	}

	public int getUpdateOperatorId() {
		return updateOperatorId;
	}

	public void setUpdateOperatorId(int updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
