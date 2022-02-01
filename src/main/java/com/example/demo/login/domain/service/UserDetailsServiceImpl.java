package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.LoginUser;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService dao;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		LoginUser user = dao.login(userId);
		return user;
	}

}
