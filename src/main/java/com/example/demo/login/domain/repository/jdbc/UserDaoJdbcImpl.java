package com.example.demo.login.domain.repository.jdbc;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
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
	
	//attendance_informationテーブルの件数を取得
	@Override
	public int countAttenDance() throws DataAccessException {
		int count4 = jdbc.queryForObject("SELECT COUNT(*) FROM attendance_information", Integer.class);
		
		return count4;
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
}
