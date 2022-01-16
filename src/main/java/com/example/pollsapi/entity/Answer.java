package com.example.pollsapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ANSWER")
public class Answer {

	@Id
	@Column (name = "ANSWER_ID")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TEXT", nullable = false)
	private String text;

	// TODO: Добавить актив

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "QUESTION_ID", insertable = false, updatable = false)
	private Question question;

	public Answer() {
	}

	public Answer(Long id, String text, Question question) {
		this.id = id;
		this.text = text;
		this.question = question;
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

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", text='" + getText() + "'" +
			", question='" + getQuestion() + "'" +
			"}";
	}

}
