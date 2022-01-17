package com.example.pollsapi.repository;

import com.example.pollsapi.entity.UserAnswers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswersRepository extends JpaRepository<UserAnswers, Long>  {
	
}
