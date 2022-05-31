package jp.motors.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dto.CorporationDto;

public class CorporationDao {

	/** �R�l�N�V���� */
	protected Connection conn;

	/**
	 * �R���X�g���N�^
	 * �R�l�N�V�������t�B�[���h�ɐݒ肷��
	 * @param conn �R�l�N�V����
	 */
	public CorporationDao(Connection conn) {
        this.conn = conn;
    }
	
	public List<CorporationDto> selectAll() throws SQLException {
		
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATION_ID");
		sb.append("        ,COMPANY_NAME");
		sb.append("        ,DOMAIN");
		sb.append("        ,BILLING_ADDRESS");
		sb.append("        ,START_AT");
		sb.append("        ,FINISH_AT");
		sb.append("        ,UPDATE_AT");
		sb.append("        ,UPDATE_NUMBER");
		sb.append("        ,UPDATE_OPERATOR_ID");
		sb.append("    FROM");
		sb.append("        CORPORATION;");

		//���s
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
        	
        	//���s
        	ResultSet rs = ps.executeQuery();
        	
		//�l���Z�b�g
        	List<CorporationDto> corporationList = new ArrayList<>();
        	while (rs.next()) {
        		CorporationDto dto = new CorporationDto();
        		dto.setCorporationId(rs.getInt("corporation_id"));
        		dto.setCompanyName(rs.getString("COMPANY_NAME"));
        		dto.setDomain(rs.getString("DOMAIN"));
        		dto.setBillingAddress(rs.getString("BILLING_ADDRESS"));
        		dto.setStartAt(rs.getTimestamp("START_AT"));
        		dto.setFinishAt(rs.getTimestamp("FINISH_AT"));
        		dto.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
        		dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
        		dto.setUpdateOperatorId(rs.getInt("UPDATE_OPERATOR_ID"));
        		corporationList.add(dto);
        	}
        	return corporationList;
        }
		
		
	}
	
	//��񎞍������ƈ�v���Ă���CorporationId
	public List<CorporationDto> selectByFinishAt() throws SQLException {
		//SQL��
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append("        CORPORATION_ID");
		sb.append("        ,FINISH_AT");
		sb.append("    FROM");
		sb.append("        CORPORATION");
		sb.append("    WHERE");
		sb.append("        FINISH_AT <= TIMESTAMP(NOW());");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
	       try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	           
	           // SQL�����s����
	           ResultSet rs = ps.executeQuery();
	         //�l���Z�b�g
	        	List<CorporationDto> corporationList = new ArrayList<>();
	        	while (rs.next()) {
	        		CorporationDto dto = new CorporationDto();
	        		dto.setCorporationId(rs.getInt("CORPORATION_ID"));
	        		dto.setFinishAt(rs.getTimestamp("FINISH_AT"));
	        		corporationList.add(dto);
	        	}
	        	return corporationList;
	        }
	       
		
	}
	
	
	
	public void insert(CorporationDto dto) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT");
		sb.append("    INTO");
		sb.append("        corporation(");
		sb.append("            Company_name");
		sb.append("            ,domain");
		sb.append("            ,billing_Address");
		sb.append("            ,update_operator_id");
		sb.append("            ,start_at");
		sb.append("            ,update_number");
		sb.append("        )");
		sb.append("    VALUES");
		sb.append("        (");
		sb.append("            ?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("            ,0");
		sb.append("        );");

		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
       try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
       	// �v���[�X�z���_�[�ɒl���Z�b�g����
           ps.setString(1, dto.getCompanyName());
           ps.setString(2, dto.getDomain());
           ps.setString(3, dto.getBillingAddress());
           ps.setInt(4, dto.getUpdateOperatorId());
           ps.setTimestamp(5, dto.getStartAt());
           
           // SQL�����s����
           ps.executeUpdate();
           
       }
	}

	public void updateCorporation(CorporationDto dto) throws SQLException {
		
		//SQL�����`
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE");
		sb.append("        CORPORATION");
		sb.append("    SET");
		sb.append("        COMPANY_NAME = ?");
		sb.append("        ,DOMAIN = ?");
		sb.append("        ,BILLING_ADDRESS = ?");
		sb.append("        ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
		sb.append("        ,UPDATE_OPERATOR_ID = ?");
		sb.append("        ,FINISH_AT = ?");
		sb.append("    WHERE");
		sb.append("        CORPORATION_ID = ?;");
	
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
	   try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
	   	// �v���[�X�z���_�[�ɒl���Z�b�g����
	       ps.setString(1, dto.getCompanyName());
	       ps.setString(2, dto.getDomain());
	       ps.setString(3, dto.getBillingAddress());
	       ps.setInt(4, dto.getUpdateOperatorId());
	       ps.setTimestamp(5, dto.getFinishAt());
	       ps.setInt(6, dto.getCorporationId());
	       
	       // SQL�����s����
	       ps.executeUpdate();
	       
	   }
	}
	    

}
