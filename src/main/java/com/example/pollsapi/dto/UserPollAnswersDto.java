package com.example.pollsapi.dto;

import java.time.Instant;
import java.util.Set;

import com.example.pollsapi.entity.UserAnswer;
import com.example.pollsapi.entity.UserPollAnswers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPollAnswersDto {
    
    private Long id;
    private Instant finalDate;
    private Long pollId;
    private Set<UserAnswer> userAnswers;
	
    public static UserPollAnswersDto fromUser (UserPollAnswers userPollAnswers) {
		
		UserPollAnswersDto userPollAnswersDto = new UserPollAnswersDto();
		userPollAnswersDto.setId(userPollAnswers.getId());
		userPollAnswersDto.setFinalDate(userPollAnswers.getFinalDate());
		userPollAnswersDto.setPollId(userPollAnswers.getPoll().getId());
		userPollAnswersDto.setUserAnswers(userPollAnswers.getUserAnswers());
		
		return userPollAnswersDto;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getFinalDate() {
		return this.finalDate;
	}

	public void setFinalDate(Instant finalDate) {
		this.finalDate = finalDate;
	}

	public Long getPollId() {
		return this.pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

	public Set<UserAnswer> getUserAnswers() {
		return this.userAnswers;
	}

	public void setUserAnswers(Set<UserAnswer> userAnswers) {
		this.userAnswers = userAnswers;
	}
	

}
