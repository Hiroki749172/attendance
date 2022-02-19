package com.example.demo.login.domain.model;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class Update {

	private String role;
	
	
	private Integer ipAddress;
	private Time loginTime;
	private Date date;
	private int punch;
	private Date attendanceDate;
	private Time startTime;
	private Time endTime;
	private LocalDateTime nowDate = LocalDateTime.now();
	
	//groups属性にインターフェースのクラスを指定することで、フィールドとグループの紐づけができます
	@NotBlank(groups=ValidationGroup1.class)
	private String userId; //ユーザーID
	
	@NotBlank(groups=ValidationGroup1.class)
	@Length(min = 8, max=100, groups=ValidationGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups=ValidationGroup3.class)
	private String password; //パスワード

	@NotBlank(groups=ValidationGroup1.class)
	@Length(min = 8, max=100, groups=ValidationGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups=ValidationGroup3.class)
	private String beforpass;//旧パスワード
	
	
	@NotBlank(groups=ValidationGroup1.class)
	private String userName; //氏名
	
	private int master; //管理者、非管理者
		
}
