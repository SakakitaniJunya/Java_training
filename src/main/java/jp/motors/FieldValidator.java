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
	 * ���̓`�F�b�N
	 * @param dto ���[�U���
	 * @return �G���[���b�Z�[�W
	 */
	public static List<String> userValidation(UserDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getEmail())) {
			errorMessageList.add("���[���A�h���X����͂��Ă�������");
		}
		
		if (dto.getEmail().length() > 255) {
			errorMessageList.add("���[���A�h���X��255�����ȓ��œ��͂��Ă�������");
		}
		
		if ("".equals(dto.getLastName())) {
			errorMessageList.add("������͂��Ă�������");
		}
		
		if ("".equals(dto.getFirstName())) {
			errorMessageList.add("������͂��Ă�������");
		}
		
		if (dto.getLastName().length() > 30) {
			errorMessageList.add("����30�����ȓ��œ��͂��Ă�������");
		}
		
		if (dto.getFirstName().length() > 30) {
			errorMessageList.add("����30�����ȓ��œ��͂��Ă�������");
		}
		
		return errorMessageList;
	}
	
	/**
	 * �^�p�ғo�^
	 * @param dto
	 * @return
	 */
	public static List<String> operatorValidation(OperatorDto dto) {
	List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getEmail())) {
			errorMessageList.add("���[���A�h���X����͂��Ă�������");
		}
		
		if (dto.getEmail().length() > 255) {
			errorMessageList.add("���[���A�h���X��255�����ȓ��œ��͂��Ă�������");
		}
		
		if ("".equals(dto.getLastName())) {
			errorMessageList.add("������͂��Ă�������");
		}
		
		if ("".equals(dto.getFirstName())) {
			errorMessageList.add("������͂��Ă�������");
		}
		
		if (dto.getLastName().length() > 30) {
			errorMessageList.add("����30�����ȓ��œ��͂��Ă�������");
		}
		
		if (dto.getFirstName().length() > 30) {
			errorMessageList.add("����30�����ȓ��œ��͂��Ă�������");
		}
		
		return errorMessageList;
	}
	
	/**
	 * �@�l�A�J�E���g���̓`�F�b�N
	 * @param dto
	 * @return
	 */
	public static List<String> corporatorValidation(CorporatorDto dto) {
		List<String> errorMessageList = new ArrayList<>();
			
			if ("".equals(dto.getEmail())) {
				errorMessageList.add("���[���A�h���X����͂��Ă�������");
			}
			
			if (dto.getEmail().length() > 255) {
				errorMessageList.add("���[���A�h���X��255�����ȓ��œ��͂��Ă�������");
			}
			
			if ("".equals(dto.getLastName())) {
				errorMessageList.add("������͂��Ă�������");
			}
			
			if ("".equals(dto.getFirstName())) {
				errorMessageList.add("������͂��Ă�������");
			}
			
			if (dto.getLastName().length() > 30) {
				errorMessageList.add("����30�����ȓ��œ��͂��Ă�������");
			}
			
			if (dto.getFirstName().length() > 30) {
				errorMessageList.add("����30�����ȓ��œ��͂��Ă�������");
			}
			
			return errorMessageList;
		}
	
	/**
	 * ���̓`�F�b�N
	 * @param dto ���[�U�p�X���[�h
	 * @return �G���[���b�Z�[�W
	 */
	public static List<String> passwordValidation(String password, String password2) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(password) || !password.equals(password2)) {
			errorMessageList.add("�V�����p�X���[�h�Ɗm�F�p�̃p�X���[�h������������܂���");
		}
		
		if ("knowledge123".equals(password)) {
			errorMessageList.add("�������p�X���[�h���ύX����Ă��܂���");
		}
		
		return errorMessageList;
	}
	
	
	/**
	 * ���̓`�F�b�N
	 * @param dto �J�e�S���쐬
	 * @return �G���[���b�Z�[�W
	 */
	public static List<String> categoryValidation(CategoryDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getCategory())) {
			errorMessageList.add("�J�e�S������͂��Ă�������");
		}
			
		if (dto.getCategory().length() > 30) {
			errorMessageList.add("�J�e�S����30�����ȓ��œ��͂��Ă�������");
		}
		
		return errorMessageList;
	}
	
	
	
	/**
	 * �@�l�ꗗ�o�^���̓��͊m�F
	 * @param dto
	 * @return
	 */
	public static List<String> corporationValidation(CorporationDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getCompanyName())) {
			errorMessageList.add("��Ж�����͂��Ă�������");
		}
			
		if (dto.getCompanyName().length() > 200) {
			errorMessageList.add("��Ж���300�����ȓ��œ��͂��Ă�������");
		}
		
		if ("".equals(dto.getDomain())) {
			errorMessageList.add("�h���C��������͂��Ă�������");
		}
			
		if (dto.getCompanyName().length() > 400) {
			errorMessageList.add("�h���C������400�����ȓ��œ��͂��Ă�������");
		}
		if ("".equals(dto.getBillingAddress())) {
			errorMessageList.add("���������͂��Ă�������");
		}
			
		if (dto.getBillingAddress().length() > 400) {
			errorMessageList.add("�������400�����ȓ��œ��͂��Ă�������");
		}
		
		return errorMessageList;
	}
	
	
	/**
	 * ���̓`�F�b�N
	 * @param dto �R�[�X�쐬
	 * @return �G���[���b�Z�[�W
	 */
	public static List<String> courseValidation(CourseDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getCourse())) {
			errorMessageList.add("�R�[�X����͂��Ă�������");
		}
		
		if (dto.getCourse().length() > 30) {
			errorMessageList.add("�R�[�X��30�����ȓ��œ��͂��Ă�������");
		}
		
		if (dto.getEstimatedStudyTime() == 0) {
			errorMessageList.add("���Ԃ���͂��Ă�������");
		}
		
		if ("".equals(dto.getCourseOverview())) {
			errorMessageList.add("�R�[�X�T�v����͂��Ă�������");
		}
		
		if (dto.getCourseOverview().length() > 65535) {
			errorMessageList.add("�R�[�X�T�v��65535�����ȓ��œ��͂��Ă�������");
		}
		
		if ("".equals(dto.getPrerequisite())) {
			errorMessageList.add("�O���������͂��Ă�������");
		}
		
		if (dto.getPrerequisite().length() > 255) {
			errorMessageList.add("�O�������255�����ȓ��œ��͂��Ă�������");
		}
		
		if ("".equals(dto.getGoal())) {
			errorMessageList.add("�S�[������͂��Ă�������");
		}
		
		if (dto.getGoal().length() > 65535) {
			errorMessageList.add("�S�[����65535�����ȓ��œ��͂��Ă�������");
		}
		
		return errorMessageList;
	}


	public static List<String> indexValidation(IndexDto dto) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getIndexs())) {
			errorMessageList.add("�ڎ�������͂��Ă�������");
		}
		
		if (dto.getIndexs().length() > 30) {
			errorMessageList.add("�ڎ�����30�����ȓ��œ��͂��Ă�������");
		}
		
		if ("".equals(dto.getContent())) {
			errorMessageList.add("�e�L�X�g���e����͂��Ă�������");
		}
		
		if (dto.getContent().length() > 65535) {
			errorMessageList.add("�e�L�X�g���e��65535�����ȓ��œ��͂��Ă�������");
		}		
		
		return errorMessageList;
	}
	
	
	
	/**
	 * ���̓`�F�b�N
	 * @param dto �R�[�X�쐬
	 * @return �G���[���b�Z�[�W
	 */
	public static List<String> problemValidation(ProblemDto dto, int count) {
		List<String> errorMessageList = new ArrayList<>();
		
		if ("".equals(dto.getProblem()) || dto.getProblem() == null) {
			errorMessageList.add("��" + count + "�̖�����͂��Ă�������");
		}
		
		if (dto.getProblem().length() > 500) {
			errorMessageList.add("��" + count + "�̖���500�����ȓ��œ��͂��Ă�������");
		}
		
		if ("".equals(dto.getProblemStatement()) || dto.getProblem() == null) {
			errorMessageList.add("��" + count + "�̖�蕶����͂��Ă�������");
		}
		
		if (dto.getProblemStatement().length() > 100) {
			errorMessageList.add("��" + count + "�̖�蕶��100�����ȓ��œ��͂��Ă�������");
		}
		
		if (dto.getCorrectSelectId() == 0) {
			errorMessageList.add("��" + count + "�̐�����I�����Ă�������");
		}
		
		
		return errorMessageList;
	}
	
	
	
	/**
	 * ���̓`�F�b�N
	 * @param dto �R�[�X�쐬
	 * @return �G���[���b�Z�[�W
	 */
	public static List<String> SelectionResultValidation(SelectionResultDto dto, int problemNumber, int selectionNumber) {
		List<String> errorMessageList = new ArrayList<>();
			
			if ("".equals(dto.getSelection())) {
				errorMessageList.add("��" + problemNumber + "�̑I����" + selectionNumber + "����͂��Ă�������");
			}
			
			if (dto.getSelection().length() > 100) {
				errorMessageList.add("��" + problemNumber + "�̑I����" + selectionNumber + "��100�����ȓ��œ��͂��Ă�������");
			}
			
			if ("".equals(dto.getResult())) {
				errorMessageList.add("��" + problemNumber + "�̌���" + selectionNumber + "����͂��Ă�������");
			}
			
			if (dto.getResult().length() > 300) {
				errorMessageList.add("��" + problemNumber + "�̌���" + selectionNumber + "��300�����ȓ��œ��͂��Ă�������");
			}
		
		return errorMessageList;
	}
	
	
	
	
}
