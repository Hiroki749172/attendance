package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface SignupDao {

	//user_masterテーブルにデータを1件insert
	public int insertOne(User user) throws DataAccessException;
	
	//attendance_informationテーブルにデータを1件insert
	public int insertFor(User user)throws DataAccessException;
		
}
