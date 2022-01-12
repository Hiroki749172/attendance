package com.example.demo.login.domain.service.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import com.example.demo.login.domain.service.RestService;

@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {
	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;
	
	//1件登録用メソッド
	@Override
	public boolean insert(User user) {
		
		int result = dao.insertOne(user);
		
		if(result == 0) { //登録に成功していれば真偽値を返す
			return false;
		} else {
			return true;
		}
	}
	
	//1件検索用メソッド
	@Override
	public User selectOne(String userId) {
		return dao.selectOne(userId);
	}

	//全件検索用メソッド
	@Override
	public List<User> selectMany() {
		return dao.selectMany();
	}

	//１件更新用メソッド
	@Override
	public boolean update(User user) {
		int result = dao.updateOne(user);
		if(result == 0) {
			return false;
		} else {
			return true;
		}
		
	}

	//1件削除用メソッド
	@Override
	public boolean delete(String userId) {
		int result = dao.deleteOne(userId);
		if(result == 0) {
			return false;
		} else {
			return true;
		}
		
	}
}
