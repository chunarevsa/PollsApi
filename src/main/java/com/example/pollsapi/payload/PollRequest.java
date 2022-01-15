package com.example.pollsapi.payload;

import java.time.Instant;

public class PollRequest {
	
	private String name;
	private String description;
	private Boolean active;
	private Instant expirationDate;

	public PollRequest() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isActive() {
		return this.active;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Instant getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Instant expirationDate) {
		this.expirationDate = expirationDate;
	}

}
