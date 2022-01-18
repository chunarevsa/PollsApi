package com.example.pollsapi.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "USER_POLLS")
public class UserPolls {

	@Id
	@Column (name = "USER_POLLS_ID")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column (name = "USER_UNIQUE_ID", unique = true, nullable = false)
	@NaturalId
	private Long userUniqueId;

	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USER_POLL_ANSWERS_ID")
	private Set<UserPollAnswers> userPollAnswers = new HashSet<>();


	public UserPolls() {
	}

	public UserPolls(Long id, 
							Long userUniqueId, 
							Set<UserPollAnswers> userPollAnswers) {
		this.id = id;
		this.userUniqueId = userUniqueId;
		this.userPollAnswers = userPollAnswers;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserUniqueId() {
		return this.userUniqueId;
	}

	public void setUserUniqueId(Long userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public Set<UserPollAnswers> getUserPollAnswers() {
		return this.userPollAnswers;
	}

	public void setUserPollAnswers(Set<UserPollAnswers> userPollAnswers) {
		this.userPollAnswers = userPollAnswers;
	}

	
}
