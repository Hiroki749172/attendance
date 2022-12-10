package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class UserDetailController {

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
	
	//ユーザー詳細画面のGET用メソッド
	@GetMapping("/userDetail/{id:.+}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("id") String userId) {
		//ユーザーID確認（デバック）
		System.out.println("userId = " + userId);
		
		//コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/userDetail :: userDetail_contents");
		
		//管理者区分用ラジオボタンの初期化
		radioMaster = initRadioMaster();
		
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMaster", radioMaster);
		
		//ユーザーIDのチェック
		if(userId != null && userId.length() > 0) {
			
			//ユーザー情報を取得
			User user = userService.selectOne(userId);

			//Userクラスをフォームクラスに変換
			form.setUserId(user.getUserId());
			form.setPassword(user.getPassword());
			form.setUserName(user.getUserName());
			form.setMaster(user.getMaster());
			
			//Modelに登録
			model.addAttribute("signupForm", form);
		}
		return "login/homeLayout";
	}
		
	//社員削除用処理
	@PostMapping(value="/userDetail", params="delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {
		System.out.println("削除ボタンの処理");
		
		//削除実行
		boolean result = userService.deleteOne(form.getUserId());
		
		if(result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}
		
		//社員一覧画面を表示
		return getUserList(model);
	}
	
	//社員更新用処理
	@PostMapping(value="/userDetail", params="update")
	public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {
		System.out.println("更新ボタンの処理");
		
		//Userインスタンスの生成
		User user = new User();
		
		//フォームクラスをUserクラスに変換
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setMaster(form.getMaster());
		
		try {
			//更新実行
			boolean result = userService.updateOne(user);
			
			if(result == true ) {
				model.addAttribute("result", "更新成功");
				
			} else {
				model.addAttribute("result", "更新失敗");
			}
		} catch(DataAccessException e) {
			model.addAttribute("result", "更新失敗（トランザクションテスト）");
		}
			//社員一覧画面を表示
			return getUserList(model);
	}
	
	//社員管理画面のGET用メソッド
	@GetMapping("/userList")
	public String getUserList(Model model) {
		//コンテンツ部分にユーザー一覧画面を表示するための文字列を登録
		model.addAttribute("contents", "login/userList :: userList_contents");
		
		radioMaster = initRadioMaster();
		model.addAttribute("radioMaster", radioMaster);
		
		radioPunch = initRadioPunch();
		model.addAttribute("radioPunch", radioPunch);
		
		//社員管理画面の生成（Modelから値を取得して表示するためです）
		List<User> userList = userService.selectManyFor();
		
		//Modelにユーザーリストを登録(複数検索結果)
		model.addAttribute("userList", userList);
		
		//データ件数を取得（カウント結果）
		int count = userService.count();
		model.addAttribute("userListCount", count);
		
		return "login/homeLayout";
	}
	
	//社員一覧のCSV出力処理
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model) {
		//社員を全件取得して、CSVをサーバーに保存する
		userService.userCsvOut();
		
		byte[] bytes = null;
		
		try {
			//サーバーに保存されているsample.csvファイルをbyteで取得する
			bytes = userService.getFile("sample.csv");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//HTTPヘッダーの設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");
		//sample.csvを戻す
		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

}
