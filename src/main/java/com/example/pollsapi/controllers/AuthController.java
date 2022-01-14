package com.example.pollsapi.controllers;

import javax.validation.Valid;

import com.example.pollsapi.payload.LoginRequest;
import com.example.pollsapi.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: swagger
// - авторизация в системе (регистрация не нужна)

@RestController
@RequestMapping("/auth/")
//@Api(value = "Authorization", description = "Авторизация пользователей")
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * Логин по почте, паролю и устройству
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/login")
	//@ApiOperation(value = "Логин по почте, паролю и устройству")
	public ResponseEntity login (@Valid @RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authService.authenticateUser(loginRequest)
			.orElseThrow(() -> new UserLoginException("аутентификации", loginRequest.getEmail()));

		JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
		logger.info("Вход в систему  " + jwtUser.getUsername());
		SecurityContextHolder.getContext().setAuthentication(authentication);  
		
		return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
					.map(RefreshToken::getToken)
					.map(refreshToken -> {
						String jwtToken = authService.createToken(jwtUser);
						return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,  jwtTokenProvider.getExpiryDuration()));
					}).orElseThrow(() -> new UserLoginException("создания токена", loginRequest.getEmail()));

	} 
}
