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

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDetailDao;

@Transactional
@Service
public class UserDetailService {

	@Autowired
	@Qualifier("UserDetailRepository")
	UserDetailDao dao;
	
	//カウント用メソッド
	public int count() {
		return dao.count();
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
	
	public List<User> selectManyFor(){
		return dao.selectManyFor();
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
		
}
