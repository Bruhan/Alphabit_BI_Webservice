package com.facility.management.usecases.auth;


import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.usecases.auth.dto.AuthRequestDTO;
import com.facility.management.usecases.auth.dto.AuthTokensDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("${spring.base.path}")
public class AuthController {
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> createNewTokens(@RequestBody AuthRequestDTO authenticationRequest) throws Exception {

		AuthTokensDTO authTokensDTO = authService.createNewTokens(authenticationRequest);

		if(authTokensDTO.getStatus() == HttpStatus.OK.value()) {
			ResultDao resultDao = new ResultDao();
			resultDao.setStatusCode(authTokensDTO.getStatus());
			resultDao.setResults(authTokensDTO);
			resultDao.setMessage(authTokensDTO.getMessage());

			return ResponseEntity.ok(resultDao);
		} else {
			ResultDao resultDao = new ResultDao();
			resultDao.setStatusCode(authTokensDTO.getStatus());
			resultDao.setResults(authTokensDTO);
			resultDao.setMessage(authTokensDTO.getMessage());

			return ResponseEntity.ok(resultDao);
		}

	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> createAccessToken(@RequestBody AuthTokensDTO authRefresh) throws Exception {
		return ResponseEntity.ok(authService.createAccessToken(authRefresh));
	}

}
