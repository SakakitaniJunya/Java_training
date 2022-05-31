package jp.motors.dto;

import java.sql.Timestamp;

public class ProgressDto {

	/* êiíªID */
	private int progressId;
	
	/* óòópé“ID */
	private int userId;
	
	/* ñ⁄éüID */
	private int indexId;

	/* éÛçuäÆóπéûä‘ */
	private Timestamp lecturesFinishAt;
	
	/* éÛçuäÆóπéûä‘ */
	private String lecturesFinishTime;
	
	private String lastName;
	
	private String firstName;
	
	private String category;
	
	private String course;
	
	private String indexs;

	public String getLecturesFinishTime() {
		return lecturesFinishTime;
	}

	public void setLecturesFinishTime(String lecturesFinishTime) {
		this.lecturesFinishTime = lecturesFinishTime;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getIndexs() {
		return indexs;
	}

	public void setIndexs(String indexs) {
		this.indexs = indexs;
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

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public Timestamp getLecturesFinishAt() {
		return lecturesFinishAt;
	}

	public void setLecturesFinishAt(Timestamp lecturesFinishAt) {
		this.lecturesFinishAt = lecturesFinishAt;
	}

}
