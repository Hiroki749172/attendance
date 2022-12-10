package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface HomeDao {

	//勤怠画面に表示（氏名、勤怠区分、勤怠情報時間）するための取得処理
	public User selectHome(String userId) throws DataAccessException;
	
	public int updateStart(String userId) throws DataAccessException;
	
	public int updateEnd(String userId) throws DataAccessException;
	
}
