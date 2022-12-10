package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.HomeService;


@Controller
public class HomeController {
	
	@Autowired
	HomeService homeService;
	
	//ホーム画面用のGETメソッド
	@GetMapping("/home")
	public String getHome(Model model) {
		//コンテンツ部分にホーム画面を表示するための文字列を登録
		model.addAttribute("contents", "login/home :: home_contents");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		System.out.print(userId);
		User userHome = homeService.selectHome(userId);
		model.addAttribute("userHome", userHome);
		return "login/homeLayout";
	}
	
	@PostMapping(value="/home", params="workStart")
	public String postWorkStart(Model model) {
		
		model.addAttribute("contents", "login/admin :: admin_contents");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		
		boolean work = homeService.updateStart(userId);
		
		if (work == true) {
			model.addAttribute("work", "出勤処理完了");
		}
		
		return getHome(model);
	}
	
	@PostMapping(value="/home", params="workEnd")
	public String postWorkEnd(Model model) {
		
		model.addAttribute("contents", "login/admin :: admin_contents");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		boolean work = homeService.updateEnd(userId);
		if (work == true) {
			model.addAttribute("work", "退勤処理完了");
		}
		return getHome(model);
	}
		
}
