package com.example.pollsapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTION")
public class Question {

	@Id
	@Column (name = "QUESTION_ID")
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@Column(name = "TEXT", nullable = false)
	private String text;

	@Column(name = "NAME", nullable = false)
	@Enumerated (EnumType.STRING)
	private QuestionType questionType;

	public Question() {
	}

	public Question(String text, QuestionType questionType) {
		this.text = text;
		this.questionType = questionType;
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

}
