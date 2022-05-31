package jp.motors.dto;

import java.sql.Timestamp;

public class CourseDto {
	
	/* コースID */
	private int courseId;
	
	/* カテゴリID */
	private int categoryId;
	
	/* カテゴリ名　*/
	private String category;
	
	/* コース名 */
	private String course;
	
	/* コース概要 */
	private String courseOverview;
	
	/* コース目次 */
	private String indexs;
	
	/* 前提条件 */
	private String prerequisite;
	
	/* ゴール */
	private String goal;
	
	/* 学習目安時間 */
	private int estimatedStudyTime;
	
	/* 更新ユーザID */
	private int updateOperatorId;
	
	/* 最終更新時刻 */
	private Timestamp updateAt;
	
	/* 更新番号 */
	private int updateNumber;
	
	private Boolean isFreeCourseFlg;
	
	private int score;
	
	private Boolean deleteFlg;

	public Boolean getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getIndexs() {
		return indexs;
	}

	public void setIndexs(String indexs) {
		this.indexs = indexs;
	}

	public Boolean getIsFreeCourseFlg() {
		return isFreeCourseFlg;
	}

	public void setIsFreeCourseFlg(Boolean isFreeCourseFlg) {
		this.isFreeCourseFlg = isFreeCourseFlg;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getUpdateOperatorId() {
		return updateOperatorId;
	}

	public void setUpdateOperatorId(int updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getCourseOverview() {
		return courseOverview;
	}

	public void setCourseOverview(String courseOverview) {
		this.courseOverview = courseOverview;
	}

	public String getPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public int getEstimatedStudyTime() {
		return estimatedStudyTime;
	}

	public void setEstimatedStudyTime(int estimatedStudyTime) {
		this.estimatedStudyTime = estimatedStudyTime;
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
