package com.example.demo.login.domain.model;

import java.sql.Time;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SelectDate {
	@NotNull(groups = ValidationGroup1.class)
	@DateTimeFormat(pattern="hh:mm")
	private Time startTime;
	
	@DateTimeFormat(pattern="hh:mm")
	private Time endTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date attendanceDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;
	
	@NotBlank(groups=ValidationGroup1.class)
	private String userId; //ユーザーID
	
	@NotBlank(groups=ValidationGroup1.class)
	@Length(min = 8, max=100, groups=ValidationGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups=ValidationGroup3.class)
	private String password; //パスワード
	
	@NotBlank(groups=ValidationGroup1.class)
	private String userName; //氏名
	
	private int punch;
	private int master; //管理者、非管理者]
}
