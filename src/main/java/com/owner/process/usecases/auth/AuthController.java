package com.owner.process.usecases.auth;


import com.owner.process.usecases.auth.dao.AuthRequestDao;
import com.owner.process.usecases.auth.dao.AuthTokensDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${spring.base.path}")
public class AuthController {
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<AuthTokensDao> createNewTokens(@RequestBody AuthRequestDao authenticationRequest) throws Exception {
		return ResponseEntity.ok(authService.createNewTokens(authenticationRequest));
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> createAccessToken(@RequestBody AuthTokensDao authRefresh) throws Exception {
		return ResponseEntity.ok(authService.createAccessToken(authRefresh));
	}

}
