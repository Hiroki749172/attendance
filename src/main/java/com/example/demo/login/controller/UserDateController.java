package com.example.demo.login.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class UserDateController {
	
	@Autowired
	UserService userService;
	
	//管理者区分のラジオボタン用の変数
	private Map<String, Integer> radioMaster;
	
	//ラジオボタンの初期化メソッド（ユーザー登録画面と同じ）
	private Map<String, Integer> initRadioMaster(){
		Map<String, Integer> radio = new LinkedHashMap<>();
		//管理者、非管理者をMapに格納
		radio.put("管理者", 1);
		radio.put("非管理者", 0);
		return radio;
	}
	
	private Map<String, Integer> radioPunch;
	
	private Map<String, Integer> initRadioPunch(){
		Map<String, Integer> radio = new LinkedHashMap<>();
		radio.put("出勤", 1);
		radio.put("退勤", 0);
		return radio;
	}
	
	@GetMapping(value="/userDate")
	public String getUserDate(Model model) {
		//コンテンツ部分にユーザー一覧画面を表示するための文字列を登録
		model.addAttribute("contents", "login/userDate :: userDate_contents");
		radioMaster = initRadioMaster();
		model.addAttribute("radioMaster", radioMaster);
		
		radioPunch = initRadioPunch();
		model.addAttribute("radioPunch", radioPunch);

		return "login/homeLayout";
	}
	@PostMapping(value="/userDate", params="selectDate")
	public String postUserDate(@RequestParam("calendar") String dates, Model model) {
		System.out.println("社員一覧検索処理");
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date newDate = dateFormat.parse(dates);
			Date date = newDate;
			System.out.println(newDate);
			List<User> dateList = userService.postList(date);
			model.addAttribute("userListSelect", dateList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getUserDate(model);
		
	}
}
