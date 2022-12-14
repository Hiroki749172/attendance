package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

//一般的にビジネスロジックを担当するサービスクラスに付ける
@Transactional
@Service
public class UserService {
	//@Autowiredと一緒に@Qualifierを使用するとどのBeanを使用するか指定することができます
	//Bean名をセットすることで@Autowiredする際にどのクラスを使用するか指定できます
	//インターフェースを継承したクラスが２つ(UserDaoJdbcImplとUserDaoJdbcImpl2)がある場合付ける
	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;
	
	public User selectFor(String userId){
		return dao.selectFor(userId);
	}

	public int countAttenDance() {
		return dao.countAttenDance();
	}
}