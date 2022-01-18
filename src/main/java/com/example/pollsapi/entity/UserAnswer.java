package com.example.pollsapi.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_ANSWER")
public class UserAnswer {

	@Id
	@Column (name = "USER_ANSWER_ID")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column (name = "QUESTION_BODY")
	private String questionBody;

	@Column (name = "USER_ANSWER_BODY")
	private String userAnswerBody;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
   @PrimaryKeyJoinColumn(name = "question_id", referencedColumnName = "id")
	private Question question;

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "USER_ANSWERS_ID", insertable = false, updatable = false)
	private UserPollAnswers userPollAnswers;

	public UserAnswer() {
	}

	public UserAnswer(Long id, String questionBody, String userAnswerBody, Question question, UserPollAnswers userPollAnswers) {
		this.id = id;
		this.questionBody = questionBody;
		this.userAnswerBody = userAnswerBody;
		this.question = question;
		this.userPollAnswers = userPollAnswers;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionBody() {
		return this.questionBody;
	}

	public void setQuestionBody(String questionBody) {
		this.questionBody = questionBody;
	}

	public String getUserAnswerBody() {
		return this.userAnswerBody;
	}

	public void setUserAnswerBody(String userAnswerBody) {
		this.userAnswerBody = userAnswerBody;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public UserPollAnswers getUserPollAnswers() {
		return this.userPollAnswers;
	}

	public void setUserPollAnswers(UserPollAnswers userPollAnswers) {
		this.userPollAnswers = userPollAnswers;
	}

}
