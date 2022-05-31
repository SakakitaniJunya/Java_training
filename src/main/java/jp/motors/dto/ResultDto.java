package jp.motors.dto;

import java.sql.Timestamp;

public class ResultDto {

	/* ���H��茋��ID */
	private int resultId;
	
	/* ���p��ID */
	private int userId;
	
	/* ���H���ID */
	private int problemId;
	
	/* �R�[�XID */
	private int courseId;
	
	/* �I�������I������ID */
	private int selectedSelectionId;

	/* ���s���� */
	private Timestamp testAt;
	
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSelectedSelectionId() {
		return selectedSelectionId;
	}

	public void setSelectedSelectionId(int selectedSelectionId) {
		this.selectedSelectionId = selectedSelectionId;
	}
	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public Timestamp getTestAt() {
		return testAt;
	}

	public void setTestAt(Timestamp testAt) {
		this.testAt = testAt;
	}

}
