package com.example.demo.login.domain.repository.jdbc;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDetailDao;

@Repository("UserDetailRepository")
public class UserDetailRepository implements UserDetailDao {

	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	UserDaoJdbcImpl dao;
	
	//user_masterテーブルの件数を取得
	@Override
	public int count() throws DataAccessException {
		//全件取得してカウント
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM user_master", Integer.class);
		return count;
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
			dao.updateAdmin(user);
		} else {
			dao.updateGeneral(user);
		}
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
}
