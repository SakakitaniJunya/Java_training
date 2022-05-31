package jp.motors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.motors.dao.ClaimDao;
import jp.motors.dao.CorporationDao;
import jp.motors.dao.CorporatorDao;
import jp.motors.dao.UserDao;
import jp.motors.dto.CorporationDto;


public class Batch {
	
	public static void main(String[] args) {
	
		String url = "jdbc:mysql://localhost:3306/codetrain?useSSL=false&serverTimezone=Asia/Tokyo";
		String user = "app-user";
		String password = "CODETRAIN";

		//月末に行う処理
		try(Connection conn = DriverManager.getConnection(url, user, password);
				Statement st = conn.createStatement();){
			
			//請求確認
			ClaimDao claimDao = new ClaimDao(conn);
			claimDao.insert();
			
			try {
			
				UserDao userDao = new UserDao(conn);
				//削除予定になっているユーザの論理削除(deleteAt)
				userDao.deleteByDeleteAt();
				
				// トランザクションを開始する
				conn.setAutoCommit(false);
				//解約予定が今日の法人
				CorporationDao corporationDao = new CorporationDao(conn);
				List<CorporationDto> corporationList = new ArrayList<>();
				corporationList = corporationDao.selectByFinishAt();
	
				int count = 0;
				//ユーザ削除&法人アカウント(法人解約になっている)
				for (CorporationDto corporationDto : corporationList) {
					//ユーザ論理削除(メールアドレス、名前)
				
					count = userDao.deleteByCorporationId(corporationDto.getCorporationId());
					
					if (count == 0) {
						throw new BusinessLogicException("排他制御(楽観ロック）例外");
					}
					// コミットする
					conn.commit();
					
					//法人アカウントの削除(法人解約になっている)
					CorporatorDao corporatorDao = new CorporatorDao(conn);
					 count = corporatorDao.deleteByCorporationId(corporationDto.getCorporationId());
					 
						if (count == 0) {
							throw new BusinessLogicException("排他制御(楽観ロック）例外");
						}
						// コミットする
						conn.commit();
				}
						
			} catch (BusinessLogicException e) {
				// ロールバックする
				conn.rollback();
		
			} catch (SQLException e) {
				
				// ロールバックする
				conn.rollback();
				throw e;
				
			} finally {
				conn.setAutoCommit(true);
			}
			
		} catch (SQLException e) {
			
			// 例外メッセージを出力表示
			e.printStackTrace();
			
		}
			
	}

}

