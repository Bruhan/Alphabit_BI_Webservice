package com.facility.management.usecases.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class AuthTokensDTO {
	private  String accessToken;
	private  String refreshToken;

	@JsonIgnore
	private int status;

	@JsonIgnore
	private String message;
}
