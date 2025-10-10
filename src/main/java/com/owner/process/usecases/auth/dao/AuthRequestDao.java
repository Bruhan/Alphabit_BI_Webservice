package com.owner.process.usecases.auth.dao;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequestDao {
	private String username;
	private String password;
	private String application;
}
