package com.example.pollsapi.service;

import com.example.pollsapi.payload.QuestionRequest;

public interface DeleteInterface {
	
	public void delete(Long id);

	public Object addQuestion(QuestionRequest qestionRequest, Long pollId);

	public Object editQuestion(QuestionRequest qestionRequest, Long priceId);

}
