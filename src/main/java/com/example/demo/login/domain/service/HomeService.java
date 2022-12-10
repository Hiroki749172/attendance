package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.HomeDao;

@Transactional
@Service
public class HomeService {

	@Autowired
	@Qualifier("HomeRepository")
	HomeDao dao;
	
	public User selectHome(String userId) {
		return dao.selectHome(userId);
	}
	
	public boolean updateStart(String userId) {
		//1件更新
		int rowNumber = dao.updateStart(userId);
		
		//判定用定数
		boolean result = false;
		if(rowNumber > 0) { //0より大きい値が返ってきたらupdate成功
			//update成功
			result = true;
		}
		return result;
	}
	public boolean updateEnd(String userId) {
		//1件更新
		int rowNumber = dao.updateEnd(userId);
		
		//判定用定数
		boolean result = false;
		if(rowNumber > 0) { //0より大きい値が返ってきたらupdate成功
			//update成功
			result = true;
		}
		return result;
	}
}
