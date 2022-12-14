package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	//ログイン画面のGET用コントローラー
	@GetMapping("/login")
	public String getLogin(Model model) {
		//login.htmlに画面遷移
		User user = new User();
		
		try {
			boolean result = loginService.insertUser(user);
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
	public String getAdminLogin(Model model) {
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
	
	//ログアウト用メソッド
	@PostMapping("/logout")
	public String postLogout() {
		//ログイン画面にリダイレクト
		return "redirect:/login";
	}
	
	//管理者権限専用画面のGET用メソッド
	@GetMapping("/admin")
	public String getAdmin(Model model) {
		//コンテンツ部分に社員編集を表示する為の文字列を登録
		model.addAttribute("contents", "login/admin :: admin_contents");
		return "login/homeLayout";
	}
}