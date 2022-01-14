package com.example.pollsapi.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {

	@NotBlank(message = "Login Email cannot be blank")
	//@ApiModelProperty(value = "Email", required = true, allowableValues = "NonEmpty String")
   private String email;
	
	@NotNull(message = "Login password cannot be blank")
	//@ApiModelProperty(value = "Пароль", required = true, allowableValues = "NonEmpty String")
   private String password;

	public LoginRequest() {
	}

	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	} 

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
