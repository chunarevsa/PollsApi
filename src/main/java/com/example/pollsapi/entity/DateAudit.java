package com.example.pollsapi.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties (value = {"create", "update"}, allowGetters = true)
public abstract class DateAudit implements Serializable {

	@CreatedDate
	@JoinColumn (nullable = false, updatable = false)
	private Instant created;

	@JoinColumn (nullable = false, updatable = true)
	private Instant expirationDate;

	public Instant getCreated() {
		return this.created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public Instant getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Instant expirationDate) {
		this.expirationDate = expirationDate;
	}

}
