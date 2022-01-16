package com.example.pollsapi.payload;

public class StartPollRequest {

	private Long userId;

	public StartPollRequest() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
