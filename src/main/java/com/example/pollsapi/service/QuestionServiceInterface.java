package com.example.pollsapi.service;

import java.util.Optional;

import com.example.pollsapi.entity.Question;
import com.example.pollsapi.payload.QuestionRequest;

public interface QuestionServiceInterface {
	
	public Question addQuestion(QuestionRequest qestionRequest);

	public Optional<Question> editQuestion(QuestionRequest qestionRequest, Long questionId);

}
