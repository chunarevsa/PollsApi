package com.example.pollsapi.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "QUESTION")
public class Question {

	@Id
	@Column (name = "QUESTION_ID")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column(name = "BODY", nullable = false)
	private String body;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	@Column(name = "TYPE", nullable = false)
	@Enumerated (EnumType.STRING)
	private QuestionType questionType;

	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "QUESTION_ID")
	private Set<Answer> answers = new HashSet<>();

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "POLL_ID", insertable = false, updatable = false)
	private Poll poll;

	public Question() {
	}

	public Question(Question question) {
		this(question.getBody(), 
			question.getActive(), 
			question.getQuestionType(), 
			question.getAnswers(), 
			question.getPoll());
	}

	public Question(String body, Boolean active, QuestionType questionType, Set<Answer> answers, Poll poll) {
		this.body = body;
		this.active = active;
		this.questionType = questionType;
		this.answers = answers;
		this.poll = poll;
	}

	public Long getId() {
		return this.id;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public QuestionType getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
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

	public Poll getPoll() {
		return this.poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public Set<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}


	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", body='" + getBody() + "'" +
			", active='" + isActive() + "'" +
			", questionType='" + getQuestionType() + "'" +
			", answers='" + getAnswers() + "'" +
			", poll='" + getPoll() + "'" +
			"}";
	}


}
