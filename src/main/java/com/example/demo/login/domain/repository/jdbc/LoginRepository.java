package com.example.demo.login.domain.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.LoginDao;

@Repository("LoginRepository")
public class LoginRepository implements LoginDao {

	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
    PasswordEncoder passwordEncoder;

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
//					, user.getLoginTime());
			return rowNumber;
		}
	
	
}
