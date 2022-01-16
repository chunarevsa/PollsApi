package com.example.pollsapi.service;

import java.util.Optional;
import java.util.Set;

import com.example.pollsapi.entity.Question;
import com.example.pollsapi.payload.QuestionRequest;

public interface QuestionServiceInterface {
	
	public Question addQuestion(QuestionRequest qestionRequest);

	public Optional<Question> editQuestion(QuestionRequest questionRequest, 
				Set<Question> questions, int questionQueueId);

}
