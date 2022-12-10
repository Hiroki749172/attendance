package com.example.demo.login.domain.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupForm {
	@NotBlank(groups=ValidationGroup1.class)
	private String userId; //ユーザーID
	
	@NotBlank(groups=ValidationGroup1.class)
	@Length(min = 8, max=100, groups=ValidationGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups=ValidationGroup3.class)
	private String password; //パスワード
	
	@NotBlank(groups=ValidationGroup1.class)
	private String userName; //氏名
	
	private int master; //管理者、非管理者
	
//	private Date yearmonth;
}
	
