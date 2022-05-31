package jp.motors.dto;

import java.sql.Timestamp;

public class ResultDto {

	/* 実践問題結果ID */
	private int resultId;
	
	/* 利用者ID */
	private int userId;
	
	/* 実践問題ID */
	private int problemId;
	
	/* コースID */
	private int courseId;
	
	/* 選択した選択結果ID */
	private int selectedSelectionId;

	/* 実行時刻 */
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
