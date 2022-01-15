package com.example.pollsapi.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {

	@NotBlank(message = "Username cannot be blank")
	//@ApiModelProperty(value = "Email", required = true, allowableValues = "NonEmpty String")
   private String username;
	
	@NotNull(message = "Login password cannot be blank")
	//@ApiModelProperty(value = "Пароль", required = true, allowableValues = "NonEmpty String")
   private String password;

	public LoginRequest() {
	}

	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
