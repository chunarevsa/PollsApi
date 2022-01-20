package com.example.pollsapi.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.pollsapi.entity.UserPolls;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPollsDto {
    
    private Long id;
    private String userUniequeId;
    private Set<UserPollAnswersDto> userPollAnswersDto;

    public static UserPollsDto fromUser (UserPolls userPolls) {
		
		UserPollsDto userPollsDto = new UserPollsDto();
        userPollsDto.setId(userPolls.getId());
        String uniequeId = "Ваш уникальный индентификатор :" + Long.toString(userPolls.getUserUniqueId());
        userPollsDto.setUserUniequeId(uniequeId);
        userPollsDto.setUserPollAnswersDto(userPolls.getUserPollAnswers().stream()
                .map(UserPollAnswersDto :: fromUser).collect(Collectors.toSet()));
		
		return userPollsDto;
	} 

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserUniequeId() {
        return this.userUniequeId;
    }

    public void setUserUniequeId(String userUniequeId) {
        this.userUniequeId = userUniequeId;
    }

    public Set<UserPollAnswersDto> getUserPollAnswersDto() {
        return this.userPollAnswersDto;
    }

    public void setUserPollAnswersDto(Set<UserPollAnswersDto> userPollAnswersDto) {
        this.userPollAnswersDto = userPollAnswersDto;
    }
}    
