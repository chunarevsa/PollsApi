package com.example.pollsapi.controllers;

import javax.validation.Valid;

import com.example.pollsapi.payload.LoginRequest;
import com.example.pollsapi.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	 * Логин по username и паролю
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/login")
	//@ApiOperation(value = "Логин по username и паролю ")
	public ResponseEntity login (@Valid @RequestBody LoginRequest loginRequest) {
		
		return authService.login(loginRequest);

	} 
}
