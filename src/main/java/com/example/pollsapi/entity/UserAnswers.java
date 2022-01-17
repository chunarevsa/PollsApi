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
@Table(name = "USER_ANSWERS")
public class UserAnswers {

	@Id
	@Column (name = "USER_ANSWERS_ID")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column (name = "USER_ID")
	@NaturalId
	private Long userId;

	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USER_ANSWERS_ID")
	private Set<CompletedPoll> completedPolls = new HashSet<>();

	public UserAnswers() {
	}

	public UserAnswers(Long id, Long userId, Set<CompletedPoll> completedPolls) {
		this.id = id;
		this.userId = userId;
		this.completedPolls = completedPolls;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<CompletedPoll> getCompletedPolls() {
		return this.completedPolls;
	}

	public void setCompletedPolls(Set<CompletedPoll> completedPolls) {
		this.completedPolls = completedPolls;
	}
	
}
