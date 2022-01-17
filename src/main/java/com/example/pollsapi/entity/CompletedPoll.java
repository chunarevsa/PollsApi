package com.example.pollsapi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "COMPLETED_POLL")
public class CompletedPoll extends Poll {

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "USER_ANSWERS_ID", insertable = false, updatable = false)
	private UserAnswers userAnswers;

	public CompletedPoll() {
	}

	public CompletedPoll(Poll poll) {
		super(poll);
	}

	public CompletedPoll(UserAnswers userAnswers) {
		super();
		this.userAnswers = userAnswers;
	}

	public UserAnswers getUserAnswers() {
		return this.userAnswers;
	}

	public void setUserAnswers(UserAnswers userAnswers) {
		this.userAnswers = userAnswers;
	}

}
