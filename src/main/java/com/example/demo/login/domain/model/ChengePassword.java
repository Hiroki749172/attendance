package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class ChengePassword {
	private String userId;
	private String password;
	private String beforpass;
}
