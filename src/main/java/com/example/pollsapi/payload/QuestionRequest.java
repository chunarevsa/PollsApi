package com.example.pollsapi.payload;

import java.util.LinkedHashSet;
import java.util.Set;

import com.example.pollsapi.entity.Answer;
import com.example.pollsapi.entity.QuestionType;

public class QuestionRequest {

	private String body;
	private Boolean active;
	private QuestionType questionType;
	private Set<Answer> answers = new LinkedHashSet<>();

	public QuestionRequest() {
	}

	public QuestionRequest(String body, Boolean active, QuestionType questionType, Set<Answer> answers) {
		this.body = body;
		this.active = active;
		this.questionType = questionType;
		this.answers = answers;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
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
