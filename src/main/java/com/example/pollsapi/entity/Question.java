package com.example.pollsapi.entity;

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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "QUESTION")
public class Question {

	@Id
	@Column (name = "QUESTION_ID")
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@Column(name = "TEXT", nullable = false)
	private String text;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	@Column(name = "TYPE", nullable = false)
	@Enumerated (EnumType.STRING)
	private QuestionType questionType;

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "POLL_ID", insertable = false, updatable = false)
	private Poll poll;

	public Question() {
	}

	public Question(Long id, String text, Boolean active, QuestionType questionType, Poll poll) {
		this.id = id;
		this.text = text;
		this.active = active;
		this.questionType = questionType;
		this.poll = poll;
	}

	public Long getId() {
		return this.id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
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


	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", text='" + getText() + "'" +
			", active='" + isActive() + "'" +
			", questionType='" + getQuestionType() + "'" +
			"}";
	}


}
