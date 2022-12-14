package com.example.demo.login.domain.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.SignupDao;

@Repository("SignupRepository")
public class SignupRepository implements SignupDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDaoJdbcImpl dao;

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
					dao.updateAdmin(user);
				}else {
					dao.updateGeneral(user);
				}
		return rowNumber;
	}

	//attendance_informationテーブルにデータを1件insert
	@Override
	public int insertFor(User user) throws DataAccessException {
		String sql = "INSERT INTO attendance_information("
				+ " user_id, punch, start_time)"
				+ " VALUES(?, 0, '00:00')";
		
		int rowNumber = jdbc.update(sql
				, user.getUserId());
//					, user.getPunch());
//					, attendanceDate;
//					, user.getStartTime());
//					, user.getEndTime());
		
		return rowNumber;
	}

}
