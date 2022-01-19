package com.example.pollsapi.repository;

import com.example.pollsapi.entity.UserAnswer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
	
}
