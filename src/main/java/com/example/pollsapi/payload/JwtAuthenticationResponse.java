package com.example.pollsapi.payload;

public class JwtAuthenticationResponse {
	
	private String accessToken;
   private String tokenType;
   private Long expiryDuration;

	public JwtAuthenticationResponse(String accessToken, Long expiryDuration) {
		this.accessToken = accessToken;
		this.tokenType = "Bearer ";
		this.expiryDuration = expiryDuration;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Long getExpiryDuration() {
		return this.expiryDuration;
	}

	public void setExpiryDuration(Long expiryDuration) {
		this.expiryDuration = expiryDuration;
	}

	@Override
	public String toString() {
		return "{" +
			" accessToken='" + getAccessToken() + "'" +
			", tokenType='" + getTokenType() + "'" +
			", expiryDuration='" + getExpiryDuration() + "'" +
			"}";
	}
}
