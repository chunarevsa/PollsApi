package com.example.pollsapi.service;

import java.util.Optional;

import javax.validation.Valid;

import com.example.pollsapi.exception.UserLoginException;
import com.example.pollsapi.payload.JwtAuthenticationResponse;
import com.example.pollsapi.payload.LoginRequest;
import com.example.pollsapi.security.JwtTokenProvider;
import com.example.pollsapi.security.JwtUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		return Optional.ofNullable(authenticationManager.authenticate(user));
	}

	public ResponseEntity login(LoginRequest loginRequest) {

		Authentication authentication = authenticateUser(loginRequest)
			.orElseThrow(() -> new UserLoginException("аутентификации", loginRequest.getUsername()));

		JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
		String jwtToken = jwtTokenProvider.createToken(jwtUser);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken,  jwtTokenProvider.getExpiryDuration()));
	}

}
