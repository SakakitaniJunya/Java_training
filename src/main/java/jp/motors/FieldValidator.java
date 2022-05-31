package jp.motors;

import java.util.ArrayList;
import java.util.List;


import jp.motors.dto.CategoryDto;
import jp.motors.dto.CourseDto;
import jp.motors.dto.IndexDto;
import jp.motors.dto.OperatorDto;
import jp.motors.dto.ProblemDto;
import jp.motors.dto.SelectionResultDto;
import jp.motors.dto.CorporationDto;
import jp.motors.dto.CorporatorDto;
import jp.motors.dto.UserDto;

public class FieldValidator {

	/**
	 * 入力チェック
	 * @param dto ユーザ情報
	 * @return エラーメッセージ
	 */
	public static List<String> userValidation(UserDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getEmail())) {
			errorMessageList.add("メールアドレスを入力してください");
		}
		
		if (dto.getEmail().length() > 255) {
			errorMessageList.add("メールアドレスは255文字以内で入力してください");
		}
		
		if ("".equals(dto.getLastName())) {
			errorMessageList.add("姓を入力してください");
		}
		
		if ("".equals(dto.getFirstName())) {
			errorMessageList.add("名を入力してください");
		}
		
		if (dto.getLastName().length() > 30) {
			errorMessageList.add("姓は30文字以内で入力してください");
		}
		
		if (dto.getFirstName().length() > 30) {
			errorMessageList.add("名は30文字以内で入力してください");
		}
		
		return errorMessageList;
	}
	
	/**
	 * 運用者登録
	 * @param dto
	 * @return
	 */
	public static List<String> operatorValidation(OperatorDto dto) {
	List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getEmail())) {
			errorMessageList.add("メールアドレスを入力してください");
		}
		
		if (dto.getEmail().length() > 255) {
			errorMessageList.add("メールアドレスは255文字以内で入力してください");
		}
		
		if ("".equals(dto.getLastName())) {
			errorMessageList.add("姓を入力してください");
		}
		
		if ("".equals(dto.getFirstName())) {
			errorMessageList.add("名を入力してください");
		}
		
		if (dto.getLastName().length() > 30) {
			errorMessageList.add("姓は30文字以内で入力してください");
		}
		
		if (dto.getFirstName().length() > 30) {
			errorMessageList.add("名は30文字以内で入力してください");
		}
		
		return errorMessageList;
	}
	
	/**
	 * 法人アカウント入力チェック
	 * @param dto
	 * @return
	 */
	public static List<String> corporatorValidation(CorporatorDto dto) {
		List<String> errorMessageList = new ArrayList<>();
			
			if ("".equals(dto.getEmail())) {
				errorMessageList.add("メールアドレスを入力してください");
			}
			
			if (dto.getEmail().length() > 255) {
				errorMessageList.add("メールアドレスは255文字以内で入力してください");
			}
			
			if ("".equals(dto.getLastName())) {
				errorMessageList.add("姓を入力してください");
			}
			
			if ("".equals(dto.getFirstName())) {
				errorMessageList.add("名を入力してください");
			}
			
			if (dto.getLastName().length() > 30) {
				errorMessageList.add("姓は30文字以内で入力してください");
			}
			
			if (dto.getFirstName().length() > 30) {
				errorMessageList.add("名は30文字以内で入力してください");
			}
			
			return errorMessageList;
		}
	
	/**
	 * 入力チェック
	 * @param dto ユーザパスワード
	 * @return エラーメッセージ
	 */
	public static List<String> passwordValidation(String password, String password2) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(password) || !password.equals(password2)) {
			errorMessageList.add("新しいパスワードと確認用のパスワードが正しくありません");
		}
		
		if ("knowledge123".equals(password)) {
			errorMessageList.add("初期化パスワードが変更されていません");
		}
		
		return errorMessageList;
	}
	
	
	/**
	 * 入力チェック
	 * @param dto カテゴリ作成
	 * @return エラーメッセージ
	 */
	public static List<String> categoryValidation(CategoryDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getCategory())) {
			errorMessageList.add("カテゴリを入力してください");
		}
			
		if (dto.getCategory().length() > 30) {
			errorMessageList.add("カテゴリは30文字以内で入力してください");
		}
		
		return errorMessageList;
	}
	
	
	
	/**
	 * 法人一覧登録時の入力確認
	 * @param dto
	 * @return
	 */
	public static List<String> corporationValidation(CorporationDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getCompanyName())) {
			errorMessageList.add("会社名を入力してください");
		}
			
		if (dto.getCompanyName().length() > 200) {
			errorMessageList.add("会社名は300文字以内で入力してください");
		}
		
		if ("".equals(dto.getDomain())) {
			errorMessageList.add("ドメイン名を入力してください");
		}
			
		if (dto.getCompanyName().length() > 400) {
			errorMessageList.add("ドメイン名は400文字以内で入力してください");
		}
		if ("".equals(dto.getBillingAddress())) {
			errorMessageList.add("請求先を入力してください");
		}
			
		if (dto.getBillingAddress().length() > 400) {
			errorMessageList.add("請求先は400文字以内で入力してください");
		}
		
		return errorMessageList;
	}
	
	
	/**
	 * 入力チェック
	 * @param dto コース作成
	 * @return エラーメッセージ
	 */
	public static List<String> courseValidation(CourseDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getCourse())) {
			errorMessageList.add("コースを入力してください");
		}
		
		if (dto.getCourse().length() > 30) {
			errorMessageList.add("コースは30文字以内で入力してください");
		}
		
		if (dto.getEstimatedStudyTime() == 0) {
			errorMessageList.add("時間を入力してください");
		}
		
		if ("".equals(dto.getCourseOverview())) {
			errorMessageList.add("コース概要を入力してください");
		}
		
		if (dto.getCourseOverview().length() > 65535) {
			errorMessageList.add("コース概要は65535文字以内で入力してください");
		}
		
		if ("".equals(dto.getPrerequisite())) {
			errorMessageList.add("前提条件を入力してください");
		}
		
		if (dto.getPrerequisite().length() > 255) {
			errorMessageList.add("前提条件は255文字以内で入力してください");
		}
		
		if ("".equals(dto.getGoal())) {
			errorMessageList.add("ゴールを入力してください");
		}
		
		if (dto.getGoal().length() > 65535) {
			errorMessageList.add("ゴールは65535文字以内で入力してください");
		}
		
		return errorMessageList;
	}


	public static List<String> indexValidation(IndexDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getIndexs())) {
			errorMessageList.add("目次名を入力してください");
		}
		
		if (dto.getIndexs().length() > 30) {
			errorMessageList.add("目次名は30文字以内で入力してください");
		}
		
		if ("".equals(dto.getContent())) {
			errorMessageList.add("テキスト内容を入力してください");
		}
		
		if (dto.getContent().length() > 65535) {
			errorMessageList.add("テキスト内容は65535文字以内で入力してください");
		}		
		
		return errorMessageList;
	}
	
	
	
	/**
	 * 入力チェック
	 * @param dto コース作成
	 * @return エラーメッセージ
	 */
	public static List<String> problemValidation(ProblemDto dto, int count) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getProblem()) || dto.getProblem() == null) {
			errorMessageList.add("問" + count + "の問題を入力してください");
		}
		
		if (dto.getProblem().length() > 500) {
			errorMessageList.add("問" + count + "の問題は500文字以内で入力してください");
		}
		
		if ("".equals(dto.getProblemStatement()) || dto.getProblem() == null) {
			errorMessageList.add("問" + count + "の問題文を入力してください");
		}
		
		if (dto.getProblemStatement().length() > 100) {
			errorMessageList.add("問" + count + "の問題文は100文字以内で入力してください");
		}
		
		if (dto.getCorrectSelectId() == 0) {
			errorMessageList.add("問" + count + "の正解を選択してください");
		}
		
		
		return errorMessageList;
	}
	
	
	
	/**
	 * 入力チェック
	 * @param dto コース作成
	 * @return エラーメッセージ
	 */
	public static List<String> SelectionResultValidation(SelectionResultDto dto, int problemNumber, int selectionNumber) {
		List<String> errorMessageList = new ArrayList<>();
			
			if ("".equals(dto.getSelection())) {
				errorMessageList.add("問" + problemNumber + "の選択肢" + selectionNumber + "を入力してください");
			}
			
			if (dto.getSelection().length() > 100) {
				errorMessageList.add("問" + problemNumber + "の選択肢" + selectionNumber + "は100文字以内で入力してください");
			}
			
			if ("".equals(dto.getResult())) {
				errorMessageList.add("問" + problemNumber + "の結果" + selectionNumber + "を入力してください");
			}
			
			if (dto.getResult().length() > 300) {
				errorMessageList.add("問" + problemNumber + "の結果" + selectionNumber + "は300文字以内で入力してください");
			}
		
		return errorMessageList;
	}
	
	
	
	
}
