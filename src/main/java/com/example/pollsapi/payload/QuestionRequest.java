package com.example.pollsapi.payload;

import com.example.pollsapi.entity.QuestionType;

public class QuestionRequest {

	private String text;
	private Boolean active;
	private QuestionType questionType;

	public QuestionRequest() {
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


}
