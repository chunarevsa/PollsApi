package com.example.pollsapi.payload;

import java.util.LinkedHashSet;
import java.util.Set;

import com.example.pollsapi.entity.Answer;
import com.example.pollsapi.entity.QuestionType;

public class QuestionRequest {

	private String text;
	private Boolean active;
	private QuestionType questionType;
	private Set<Answer> answers = new LinkedHashSet<>();

	public QuestionRequest() {
	}

	public QuestionRequest(String text, Boolean active, QuestionType questionType, Set<Answer> answers) {
		this.text = text;
		this.active = active;
		this.questionType = questionType;
		this.answers = answers;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
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

	public QuestionType getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public Set<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

}
