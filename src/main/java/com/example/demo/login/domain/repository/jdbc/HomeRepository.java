package com.example.demo.login.domain.repository.jdbc;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.HomeDao;

@Repository("HomeRepository")
public class HomeRepository implements HomeDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public int updateStart(String userId) throws DataAccessException{
		int rowNumber = jdbc.update("UPDATE attendance_information SET punch = 1, start_time = CURRENT_TIME() WHERE user_id = ?", userId);
		return rowNumber;
	}
	
	@Override
	public int updateEnd(String userId) throws DataAccessException{
		String sql = "UPDATE attendance_information SET punch = 0, end_time = CURRENT_TIME() WHERE user_id = ?";
		int rowNumber = jdbc.update(sql, userId);
		return rowNumber;
	}
	
	public User punch(String userId) {
		User user = new User();
		if (user.getPunch() == 1) {
			updateStart(userId);
		} else {
			updateEnd(userId);
		}
		return user;
	}
	
	//勤怠画面に表示（氏名、勤怠区分、勤怠情報時間）するための取得処理
	@Override
	public User selectHome(String userId) throws DataAccessException{
		Map<String, Object> map = jdbc.queryForMap(
				"SELECT *"
				+ " FROM user_master INNER JOIN attendance_information ON user_master.user_id = attendance_information.user_id"
				+ " WHERE user_master.user_id = ?", userId);
		User user = new User();
		user.setUserId((String) map.get("user_id"));
		user.setUserName((String) map.get("user_name"));
		user.setPunch((int) map.get("punch"));
		user.setAttendanceDate((Date) map.get("attendance_date"));
		user.setStartTime((Time) map.get("start_time"));
		user.setEndTime((Time) map.get("end_time"));
		System.out.println(user);
		return user;
	}

}
