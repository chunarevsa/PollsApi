package com.example.pollsapi.repository;

import com.example.pollsapi.entity.Question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
}
