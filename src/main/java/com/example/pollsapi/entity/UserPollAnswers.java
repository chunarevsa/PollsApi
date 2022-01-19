package com.example.pollsapi.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_POLL_ANSWERS")
public class UserPollAnswers {

	@Id
	@Column (name = "USER_POLL_ANSWERS_ID")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "USER_POLLS_ID", insertable = false, updatable = false)
	private UserPolls userPoll;

	@Column (name = "FINAL_DATE", updatable = false)
	private Instant finalDate;

	@OneToOne
	@JoinColumn(name = "poll_id", referencedColumnName = "poll_id")
	private  Poll poll;

	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USER_POLL_ANSWERS_ID")
	private Set<UserAnswer> userAnswers = new HashSet<>();

	public UserPollAnswers() {
		this.finalDate = Instant.now();
	}

	public UserPollAnswers(Long id, Poll poll, UserPolls userPoll, Instant finalDate, Set<UserAnswer> userAnswers) {
		this.id = id;
		this.poll = poll;
		this.userPoll = userPoll;
		this.finalDate = Instant.now();
		this.userAnswers = userAnswers;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Poll getPoll() {
		return this.poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public UserPolls getUserPoll() {
		return this.userPoll;
	}

	public void setUserPoll(UserPolls userPoll) {
		this.userPoll = userPoll;
	}

	public Instant getFinalDate() {
		return this.finalDate;
	}

	public Set<UserAnswer> getUserAnswers() {
		return this.userAnswers;
	}

	public void setUserAnswers(Set<UserAnswer> userAnswers) {
		this.userAnswers = userAnswers;
	}
	
}
