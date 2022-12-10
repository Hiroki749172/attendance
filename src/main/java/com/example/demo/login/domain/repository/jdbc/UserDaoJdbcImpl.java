package com.example.demo.login.domain.repository.jdbc;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.ChengePassword;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
    PasswordEncoder passwordEncoder;
		
	//user_masterテーブルの件数を取得
	@Override
	public int count() throws DataAccessException {
		//全件取得してカウント
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM user_master", Integer.class);
		return count;
	}
	
	//user_masterテーブルにデータを1件insert
	@Override
	public int insertOne(User user) throws DataAccessException{
		
		//パスワード暗号化
		String password = passwordEncoder.encode(user.getPassword());
		
		//1件登録
		String sql = "INSERT INTO user_master("
				+ " user_id,"
				+ " password,"
				+ " user_name,"
				+ " master)"
				+ " VALUES(?, ?, ?, ?)"; 
				
				//1件登録
				int rowNumber =jdbc.update(sql
				, user.getUserId()
				, password
				, user.getUserName()
				, user.getMaster());
				if(user.getMaster() == 1) {
					updateAdmin(user);
				}else {
					updateGeneral(user);
				}
		return rowNumber;
	}
	
	//user_masterテーブルのデータを1件取得
	@Override
	public User selectOne(String userId) throws DataAccessException {
		//1件取得するためqueryForMapをつかう第1引数にSQL文、第2引数にPreparedStatement
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM user_master"
				+ " WHERE user_id = ?", userId);
		
		//結果返却用の変数
		User user = new User();
		
		//取得したデータを結果返却用の変数にセットしていく
		user.setUserId((String) map.get("user_id"));
		user.setPassword((String) map.get("password"));
		user.setUserName((String) map.get("user_name"));
		user.setMaster((int) map.get("master"));
		return user;
	}
	
	//user_masterテーブルのパスワードを1件取得
	@Override
	public User selectPass(String password) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT password FROM user_master WHERE password = ?", password);
		User user = new User();
		
		user.setPassword((String) map.get("password"));
		
		return user;
	}
	
	//user_masterテーブルの全データを取得
	@Override
	public List<User> selectMany() throws DataAccessException {
		//user_masterテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user_master");
		
		//結果返却用の変数
		List<User> userList = new ArrayList<>();
		
		//取得したデータを結果返却用のListに格納していく
		for(Map<String, Object> map:getList) {
			//Userインスタンスの生成
			User user = new User();
			
			//Userインスタンスに取得したデータをセットする
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setMaster((int)map.get("master"));
			
			//結果返却用のListに追加
			userList.add(user);
		}
		return userList;
	}
	
	@Override
	public int updateAdmin(User user) throws DataAccessException {
		String sql = "UPDATE user_master SET"
				+ " role = 'ROLE_ADMIN'"
				+ " WHERE user_id = ?";
		int rowNumber = jdbc.update(sql, user.getUserId());
		return rowNumber;
	}
	
	@Override
	public int updateGeneral(User user) throws DataAccessException {
		String sql = "UPDATE user_master SET"
				+ " role = 'ROLE_GENERAL'"
				+ " WHERE user_id = ?";
		int rowNumber = jdbc.update(sql, user.getUserId());
		return rowNumber;
	}
	
	
	
	//user_masterテーブルを1件更新
	@Override
	public int updateOne(User user) throws DataAccessException {
		//insertの時と同様にupdate文を使用
		//1件更新
		String sql = "UPDATE USER_MASTER SET"
				+ " user_name = ?,"
				+ " master = ?"
				+ " WHERE user_id = ?";
		
		//1件更新
		int rowNumber = jdbc.update(sql
				, user.getUserName()
				, user.getMaster()
				, user.getUserId());
		
		if(user.getMaster() == 1) {
			updateAdmin(user);
		} else {
			updateGeneral(user);
		}
		return rowNumber;
	}

	@Override
	public int updatePass(ChengePassword chenge, String userId) throws DataAccessException {
		//パスワード暗号化
		String password = passwordEncoder.encode(chenge.getPassword());
		String beforpass = passwordEncoder.encode(chenge.getBeforpass());//password
		System.out.println(beforpass);
			String sql = "UPDATE USER_MASTER SET password = ? WHERE user_id = ?";
			int rowNumber = jdbc.update(sql, password, userId);
		return rowNumber;
		
	}
	//user_masterテーブルを1件削除
	@Override
	public int deleteOne(String userId) throws DataAccessException {
		//１件削除
		int rowNumber = jdbc.update("DELETE FROM user_master WHERE user_id = ?", userId);
		
		return rowNumber;
	}
	
	//SQL取得結果をサーバーにCSVで保存する
	@Override
	public void userCsvOut() throws DataAccessException {
		//user_masterテーブルのデータを全件取得するSQL
		String sql ="SELECT * FROM user_master";
		
		//ResultSetExtractorの生成	
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		
		//SQL実行＆CSV出力
		jdbc.query(sql, handler);
	}

	//general_historyテーブルにデータを1件insert
	@Override
	public int insertUser(User user) throws DataAccessException {
		String sql = "INSERT INTO general_history(user_id,"
				+ " ip_address,"
				+ " login_time)"
				+ " VALUES(?, ?, CURTIME())";
		int rowNumber =jdbc.update(sql
				, user.getUserId()
				, user.getIpAddress());
//				, user.getLoginTime());
		return rowNumber;
	}
	
	//attendance_informationテーブルの件数を取得
	@Override
	public int countAttenDance() throws DataAccessException {
		int count4 = jdbc.queryForObject("SELECT COUNT(*) FROM attendance_information", Integer.class);
		
		return count4;
	}

	//attendance_informationテーブルにデータを1件insert
	@Override
	public int insertFor(User user) throws DataAccessException {
		String sql = "INSERT INTO attendance_information("
				+ " user_id, punch, start_time)"
				+ " VALUES(?, 0, '00:00')";
		
		int rowNumber = jdbc.update(sql
				, user.getUserId());
//				, user.getPunch());
//				, attendanceDate;
//				, user.getStartTime());
//				, user.getEndTime());
		
		return rowNumber;
	}
	
	//attendance_informationテーブルのデータを１件取得
	@Override
	public User selectFor(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM attendance_information"
				+ " WHERE user_id = ?", userId);
		User user = new User();
		
		user.setUserId((String) map.get("user_id"));
		user.setPunch((int) map.get("punch"));
		user.setAttendanceDate((Date) map.get("attendance_date"));
		user.setStartTime((Time) map.get("start_time"));
		user.setEndTime((Time) map.get("end_time"));
//		punch(userId);
		return user;
	}
	
	

	@Override
	public User endTime(String userId) {
		User user = new User();
		if (user.getPunch() == 1) {
			
		}
		return null;
	}
	
//	public User startWork(String userId) {
//		String sql = "UPDATE "
//	}
	
	
	//attendance_informationテーブルの全データを取得
	@Override
	public List<User> selectManyFor() throws DataAccessException {
		
		List<Map<String, Object>> getList = jdbc.queryForList(
				"SELECT user_master.user_id, user_master.user_name, user_master.master,"
				+ " attendance_information.punch, attendance_information.attendance_date, "
				+ "attendance_information.start_time, attendance_information.end_time, attendance_information.attendance_date FROM"
				+ " user_master LEFT OUTER JOIN attendance_information"
				+ " ON user_master.user_id = attendance_information.user_id");
		List<User> userList = new ArrayList<>();
		for(Map<String, Object> map : getList) {
			User user = new User();
			user.setUserId((String) map.get("user_id"));
			user.setUserName((String) map.get("user_name"));
			user.setMaster((int) map.get("master"));
			user.setPunch((int) map.get("punch"));
			user.setAttendanceDate((Date) map.get("attendance_date"));
			user.setStartTime((Time) map.get("start_time"));
			user.setEndTime((Time) map.get("end_time"));
			userList.add(user);
		}
		return userList;
	}

//	@Override
//	public List<SignupForm> selectManyYear(Date date, String userId) throws DataAccessException {
//		List<Map<String, Object>> getList = jdbc.queryForList(
//				"SELECT punch, attendance_date, start_time, end_time FROM"
//						+ " attendance_information"
//						+ " WHERE attendance_date = ? AND user_id = ?", date, userId);
//			List<SignupForm> userList = new ArrayList<>();
//			for(Map<String, Object> map : getList) {
////				SignupForm form = new SignupForm();
////				form.setPunch((int) map.get("punch"));
////				form.setAttendanceDate((Date) map.get("attendance_date"));
////				form.setStartTime((Time) map.get("start_Time"));
////				form.setEndTime((Time)map.get("end_time"));
////				userList.add(form);
//		}
//		return userList;
//	}

	@Override
	public List<User> selectList(Date date) throws DataAccessException {
		List<Map<String, Object>> postList = jdbc.queryForList(
				"SELECT punch, attendance_date, start_time, end_time FROM"
						+ " attendance_information"
						+ " WHERE attendance_date = ?", date);
			List<User> userList = new ArrayList<>();
			for(Map<String, Object> map : postList) {
				User user = new User();
				user.setPunch((int) map.get("punch"));
				user.setAttendanceDate((Date) map.get("attendance_date"));
				user.setStartTime((Time) map.get("start_time"));
				user.setEndTime((Time) map.get("end_time"));
				userList.add(user);
		}
		return userList;
	}

	@Override
	public User selectPunch(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap(
				"SELECT punch FROM attendance_information WHERE user_id = ?", userId);
		User user = new User();
		user.setPunch((int) map.get("punch"));
		return user;
	}

	@Override
	public List<User> postList(Date date) throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList(
				"SELECT * FROM user_master INNER JOIN attendance_information"
						+ " ON user_master.user_id = attendance_information.user_id"
						+ " AND attendance_information.attendance_date = ?", date);
				List<User> userList = new ArrayList<>();
				for(Map<String, Object> map : getList) {
					User user = new User();
					user.setUserId((String) map.get("user_id"));
					user.setUserName((String) map.get("user_name"));
					user.setMaster((int) map.get("master"));
					user.setPunch((int) map.get("punch"));
					user.setAttendanceDate((Date) map.get("attendance_date"));
					user.setStartTime((Time) map.get("start_time"));
					user.setEndTime((Time) map.get("end_time"));
					userList.add(user);
				}
				return userList;
	}

	@Override
	public List<SignupForm> selectManyYear(Date date, String userId) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
