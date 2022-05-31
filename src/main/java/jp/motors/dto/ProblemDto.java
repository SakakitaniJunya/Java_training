package jp.motors.dto;

import java.sql.Timestamp;
import java.util.List;

public class ProblemDto {

	/* ���H���ID */
	private int problemId;
	
	/* �R�[�XID */
	private int courseId;
	
	/* �����e */
	private String problem;
	
	/* ��蕶 */
	private String problemStatement;

	/* ����I������ID */
	private int correctSelectId;

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

	/*�@�I������list */
	private List<SelectionResultDto> selectionResultList;

	public List<SelectionResultDto> getSelectionResultList() {
		return selectionResultList;
	}

	public void setSelectionResultList(List<SelectionResultDto> selectionResultList) {
		this.selectionResultList = selectionResultList;
	}

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getProblemStatement() {
		return problemStatement;
	}

	public void setProblemStatement(String problemStatement) {
		this.problemStatement = problemStatement;
	}

	public int getCorrectSelectId() {
		return correctSelectId;
	}

	public void setCorrectSelectId(int correctSelectId) {
		this.correctSelectId = correctSelectId;
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
