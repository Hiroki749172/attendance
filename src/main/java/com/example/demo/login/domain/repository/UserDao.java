package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface UserDao {
	//DataAccessExceptionとはSpringではDB操作で例外が発生した場合、Springが提供しているDataAccessExceptionを投げる
	
	public int updateAdmin(User user) throws DataAccessException;
	
	public int updateGeneral(User user) throws DataAccessException;
	
	//attendance_informationテーブルの件数を取得
	public int countAttenDance() throws DataAccessException;
	
	//attendance_informationテーブルのデータを１件取得
	public User selectFor(String userId) throws DataAccessException;
}