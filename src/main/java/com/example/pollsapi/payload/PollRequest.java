package com.example.pollsapi.payload;

import java.text.ParseException;

public class PollRequest {
	
	private String name;
	private String description;
	private Boolean active;
	private String expirationDate;

	public PollRequest() {
	}

	public PollRequest(String name, String description, Boolean active, String expirationDate) throws ParseException {
		this.name = name;
		this.description = description;
		this.active = active;
		this.expirationDate = expirationDate;
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

	public String getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

}
