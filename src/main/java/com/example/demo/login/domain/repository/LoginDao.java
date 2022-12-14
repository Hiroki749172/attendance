package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface LoginDao {

	//access_historyテーブルにデータを1件insert
	public int insertUser(User user) throws DataAccessException;
}
