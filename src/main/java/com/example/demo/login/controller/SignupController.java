package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.SignupService;

@Controller
public class SignupController {
	
	@Autowired
	private SignupService signupService;

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
	
	//社員登録画面のGET用コントローラー
	//Formクラスを受け取るために、引数のフォームクラス(SignupForm form)に@ModelAttributeを付けたらModelに(addAttribute)する
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, Model model) {
		//
		model.addAttribute("contents", "login/signup :: signup_contents");
		
		//ラジオボタンの初期化メソッドの呼び出し、Mapに値を入れる
		radioMaster = initRadioMaster();
		
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMaster", radioMaster);
		
		//signup.htmlに画面遷移
		return "login/homeLayout";
	}
	
	//社員登録画面のPOST用コントローラー
	//動的(状態や構成が状況に応じて変化)にするためには、Mapを用意しMapに入ったキーと値を画面に表示
	//データバインド結果を受け取るためにはBindingResultを追加。
	//バリデーションを行う場合は@Validated。パラメータに実行順序のインターフェースを指定
	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form, 
			BindingResult bindingResult, Model model) {
		
		//データバインド失敗の場合,入力チェックに引っかかった場合、社員登録画面に戻る。.hasErrors()で失敗しているか確認
		if (bindingResult.hasErrors()) {
			//GETリクエスト用のメソッドを呼び出す（ラジオボタンの変数を初期化してくれるから）。そのあと社員登録画面に戻る
			return getSignUp(form, model);
		}
		
		//formの中身をコンソールに出して確認します
		System.out.println(form);
		
		//insert用変数
		User user = new User(); //サービスクラスのメソッドに渡すUserクラスをnewしていく
		
		user.setUserId(form.getUserId());//Userクラスには画面（フォーム）から入力された値をセット後、サービスクラスのinsertメソッドを呼び出す
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setMaster(form.getMaster());
		
		//ユーザー登録処理
		boolean result = signupService.insertOne(user);
		boolean infor = signupService.insertFor(user);
		model.addAttribute("infor", infor);
		
		//ユーザー登録結果の判定
		if(result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}
		return "redirect:/home";
	}
	
	//@ExceptionHandlerの使い方
	/*Exception毎の例外処理を実装することができます。＠の引数に例外クラスを指定することで例外毎の処理を実行できる。メソッドは複数用意できる
	 * 共通エラーページに遷移するようにし、エラーメッセージをModelクラスに登録しています
	 * */
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		//例外クラスのメッセージをModelに登録
		model.addAttribute("error", "内部サーバーエラー（DB） ： ExceptionHandler");
		
		//例外クラスのメッセージをModelに登録
		model.addAttribute("message", "SignupControllerでDataAccessExceptionが発生しました。");
		
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		//例外クラスのメッセージをModelに登録
		model.addAttribute("error", "内部サーバーエラー ： ExceptionHandler");
		//例外クラスのメッセージをModelに登録
		model.addAttribute("message", "SignupControllerでDataAccessExceptionが発生しました。");
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
}
