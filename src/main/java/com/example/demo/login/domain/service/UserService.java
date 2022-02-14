package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.ChengePassword;
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
	
	
	//insertOneメソッドを呼び出し、戻り値が0より大きければ、insertが成功したという判定結果をreturnしている
	//insert用メソッド
	public boolean insertOne(User user) {
		//insert実行
		int rowNumber = dao.insertOne(user);
		
		//判定用変数
		boolean result = false;
		
		if(rowNumber > 0) {
			//insert成功
			result = true;
		}
		return result;
	}
	
	public boolean insertFor(User user) {
		//insert実行
				int rowNumber = dao.insertFor(user);
				
				//判定用変数
				boolean result = false;
				
				if(rowNumber > 0) {
					//insert成功
					result = true;
				}
				return result;
	}
	
	//カウント用メソッド
	public int count() {
		return dao.count();
	}
	
	//全件取得用メソッド
	public List<User> selectMany() {
		//全件取得
		return dao.selectMany();
	}
	
	//1件取得メソッド
	public User selectOne(String userId) {
		//selectOne実行
		return dao.selectOne(userId);
	}
	
	//1件更新メソッド
	public boolean updateOne(User user) {
		//1件更新
		int rowNumber = dao.updateOne(user);
		
		//判定用定数
		boolean result = false;
		if(rowNumber > 0) { //0より大きい値が返ってきたらupdate成功
			//update成功
			result = true;
		}
		return result;
	}
	
	//１件削除メソッド
	public boolean deleteOne(String userId) {
		//１件削除
		int rowNumber = dao.deleteOne(userId);
		
		//判定用定数
		boolean result = false;
		
		if(rowNumber > 0) {
			//delete成功
			result = true;
		}
		return result;
	}
	
	//社員一覧をCSV出力する
	public void userCsvOut() throws DataAccessException{
		//CSV出力
		dao.userCsvOut();
	}
	
	//サーバーに保存されているファイルを取得してbyte配列に変換する
	public byte[] getFile(String fileName) throws IOException{
		//ファイルシステム（デフォルト）の取得
		FileSystem fs = FileSystems.getDefault();
		
		//ファイル取得
		Path p = fs.getPath(fileName);
		
		//ファイルをbyte配列に変換
		byte[] bytes = Files.readAllBytes(p);
		
		return bytes;
	}
	
	public List<User> selectManyFor(){
		return dao.selectManyFor();
	}

	public User selectFor(String userId){
		return dao.selectFor(userId);
	}
	
	public User selectHome(String userId) {
		return dao.selectHome(userId);
	}

	public int count4() {
		return dao.count4();
	}

	public User selectPass(String password) {
		return dao.selectPass(password);
	}
	
	public boolean insertUser(User user) {
		int rowNumber = dao.insertUser(user);
		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}

	public boolean updatePass(ChengePassword chenge, String userId) {
		//1件更新
		int rowNumber = dao.updatePass(chenge, userId);
		
		//判定用定数
		boolean result = false;
		if(rowNumber > 0) { //0より大きい値が返ってきたらupdate成功
			//update成功
			result = true;
		}
		return result;
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

//サービス用のクラスを作成