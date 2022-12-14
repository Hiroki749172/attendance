package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.SignupDao;

@Transactional
@Service
public class SignupService {

	@Autowired
	@Qualifier("SignupRepository")
	SignupDao dao;
	
	//insertOneメソッドを呼び出し、戻り値が0より大きければ、insertが成功したという判定結果をreturnしている
	//insert用メソッド
	public boolean insertOne(User user) {
		//insert実行
		int rowNumber = dao.insertOne(user);
		
		//判定用変数
		boolean result = false;
		
		if(rowNumber > 0) {
			//insert成功
			result = true;
		}
		return result;
	}
	
	public boolean insertFor(User user) {
		//insert実行
		int rowNumber = dao.insertFor(user);
		
		//判定用変数
		boolean result = false;
		
		if(rowNumber > 0) {
			//insert成功
			result = true;
		}
		return result;
	}
}
