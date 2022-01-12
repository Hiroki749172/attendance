package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupForm {
	//groups属性にインターフェースのクラスを指定することで、フィールドとグループの紐づけができます
	@NotBlank(groups=ValidationGroup1.class)
	private String userId; //ユーザーID
	
	@NotBlank(groups=ValidationGroup1.class)
	@Length(min = 8, max=100, groups=ValidationGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups=ValidationGroup3.class)
	private String password; //パスワード
	
//	@NotBlank(groups=ValidationGroup1.class)
//	@Length(min = 8, max=100, groups=ValidationGroup2.class)
//	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups=ValidationGroup3.class)
//	private String beforpass;//旧パスワード
	
	
	@NotBlank(groups=ValidationGroup1.class)
	private String userName; //氏名
	
	private int master; //管理者、非管理者
	
	private Date yearmonth;
	
//	@DateTimeFormat(pattern="yyyy/MM/dd")
//	private Timestamp attendancedate() {
//		return attendancedate();
//	}
	

	
	/*この＠をフィールドに付けると画面から渡されてきた文字列を日付型に変換してくれる
	
	
	指定されたフォーマットの文字列を数値型に
	@NumberFormat(pattern="#, ##")
	private String ○○○○;
	*/
}
//Controllerクラスにインスタンスを渡すためのフォームクラス