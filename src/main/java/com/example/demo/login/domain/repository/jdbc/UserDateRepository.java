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

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDateDao;

@Repository("UserDateRepository")
public class UserDateRepository implements UserDateDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
    PasswordEncoder passwordEncoder;

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

}
