package jp.motors.dto;

public class SelectionResultDto {

	/* 選択結果ID */
	private int selectionResultId;
	
	/* 実践問題ID */
	private int problemId;

	/* 選択 */
	private String selection;
	
	/* 結果 */
	private String result;

	public int getSelectionResultId() {
		return selectionResultId;
	}

	public void setSelectionResultId(int selectionResultId) {
		this.selectionResultId = selectionResultId;
	}

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
