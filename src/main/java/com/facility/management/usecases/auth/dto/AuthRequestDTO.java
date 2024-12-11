package com.facility.management.usecases.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequestDTO {
	private String username;
	private String password;
	private String application;
	private String plant;
	private String type;
}
