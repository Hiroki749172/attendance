package com.example.demo.login.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDateDao;

@Transactional
@Service
public class UserDateService {

	@Autowired
	@Qualifier("UserDateRepository")
	UserDateDao dao;
	
	public List<User> postList(Date date) {
		//selectOne実行
		return dao.postList(date);
	}
}
