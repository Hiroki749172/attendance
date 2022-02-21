package com.example.demo.login.domain.model;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//@DataでLombokでgetterやsetterを自動で作る
@Data
public class User {
	private String userId;
	private String password;
	private String userName;
	private int master;
	private String role;
	
	private String beforpass;
	
	private Integer ipAddress;
	private Time loginTime;
	
	private int punch;
	private Date attendanceDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;
	
	private Time startTime;
	private Time endTime;
	private LocalDateTime nowDate = LocalDateTime.now();
}



/*DBから取得した値を、コントローラークラスやサービスクラスなどの間でやり取りするためのクラス
 * 
 * */
 