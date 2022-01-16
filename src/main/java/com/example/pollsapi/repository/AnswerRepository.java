package com.example.pollsapi.repository;

import com.example.pollsapi.entity.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
}
