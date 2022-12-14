package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.LoginDao;

@Transactional
@Service
public class LoginService {

	@Autowired
	@Qualifier("LoginRepository")
	LoginDao dao;
	
	public boolean insertUser(User user) {
		int rowNumber = dao.insertUser(user);
		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
}
