package com.example.pollsapi.repository;

import com.example.pollsapi.entity.UserPollAnswers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPollAnswersRepository extends JpaRepository<UserPollAnswers, Long>  {
	
}
