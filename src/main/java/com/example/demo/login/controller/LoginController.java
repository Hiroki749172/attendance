package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	//ログイン画面のGET用コントローラー
	@GetMapping("/login")
	public String getLogin(Model model) {
		//login.htmlに画面遷移
		User user = new User();
		
		try {
			boolean result = userService.insertUser(user);
			if(result == true) {
				model.addAttribute("result", "挿入成功");
			} else {
				model.addAttribute("result", "挿入失敗");
			}
		} catch(DataAccessException e) {
			model.addAttribute("result", "挿入失敗（トランザクションテスト）");
		}
		return "login/login";
	}
	
	@GetMapping("/adminlogin")
	public String getAdmin(Model model) {
		return "login/adminlogin";
	}
	
	//ログイン画面のPOST用コントローラー
	@PostMapping("/login")
	public String postLogin(Model model) {
		
		return "redirect:/home";
	}
	
	@PostMapping("/adminlogin")
	public String postAdmin(Model model) {
		return "redirect:/home";
	}
}



//localhost:8080/loginにGETメソッド、POSTメソッドでHTTPリクエストが来たら、login配下のlogin.htmlに遷移する
