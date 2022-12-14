package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface UserDetailDao {

	//user_masterテーブルの件数を取得
	public int count() throws DataAccessException;
	
	//user_masterテーブルのデータを1件取得
	public User selectOne(String userId) throws DataAccessException;
	
	//user_masterテーブルを1件更新
	public int updateOne(User user) throws DataAccessException;
	
	//user_masterテーブルを１件削除
	public int deleteOne(String userId) throws DataAccessException;
	
	//SQL取得結果をサーバーにCSVで保存する
	public void userCsvOut() throws DataAccessException;
	
	public List<User> selectManyFor() throws DataAccessException;
	
}
