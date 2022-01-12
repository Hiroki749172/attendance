package com.example.demo.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.RestService;

//REST用のコントローラークラスには@RestControllerを付ける。各メソッドの戻り値にhtmlファイル以外を指定できるようになる
@RestController
public class UserRestController {

	@Autowired
	RestService service;
	
	//ユーザー全件取得
	@GetMapping("/rest/get") //取得
	public List<User> getUserMany(){
		//ユーザー全件取得
		return service.selectMany();
	}
	
	//ユーザー１件取得
	@GetMapping("/rest/get/{id:.+}")
	public User getUserOne(@PathVariable("id") String userId) {
		//ユーザー１件取得
		return service.selectOne(userId);
	}
	
	//@RequestBodyを使うとHTTPリクエストのボディ部分を引数にマッピングする。こうするとPOSTメソッドでもユーザー情報を受け取れる
	@PostMapping("/rest/insert")
	public String postUserOne(@RequestBody User user) {
		
		//ユーザーを1件登録
		boolean result = service.insert(user);
		
		String str = "";
		
		if(result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}
		//結果用の文字列をリターン
		return str;
	}
	
	//更新は@PutMappingを使う。登録処理の場合と同様に、HTTPリクエストのボディ部分を@RequestBodyで引数をセットしている
	@PutMapping("/rest/update")
	public String putUserOne(@RequestBody User user) {
		//ユーザーを1件更新
		boolean result = service.update(user);
		
		String str = "";
		
		if(result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}
		//結果用の文字列をリターン
		return str;
	}
	
	//@DeleteMapping
	@DeleteMapping("/rest/delete/{id:.+}")
	public String deleteUserOne(@PathVariable("id") String userId) {
		//ユーザーを１件削除
		boolean result = service.delete(userId);
		String str = "";
		if(result == true) {
			str = "{\"result\":\"ok\"}";
		}else {
			str = "{\"result\":\"error\"}";
		}
		return str;
	}
}
//


