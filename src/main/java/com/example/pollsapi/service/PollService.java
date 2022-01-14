package com.example.pollsapi.service;

import java.util.Set;

import javax.validation.Valid;

import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.payload.EditPollRequest;
import com.example.pollsapi.payload.PollRequest;
import com.example.pollsapi.payload.QuestionRequest;
import com.example.pollsapi.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: активный опрос по времени и isActive
// проверка на дату, что бы не была меньше нау
@Service
public class PollService implements DeleteInterface, PollInterface {

	private final PollRepository pollRepository;
	private final QuestionService questionService;

	@Autowired
	public PollService(PollRepository pollRepository, QuestionService questionService) {
		this.pollRepository = pollRepository;
		this.questionService = questionService;
	}

	// только активных по дате и из актив
	@Override
	public Set<Poll> getPolls() {

		return null;
	}

	@Override
	public Object start() {
		return null;
	}

	@Override
	public void delete(Long id) {
	}

	@Override
	public Poll addPoll(PollRequest pollRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Poll editPoll(Long id, EditPollRequest editPollRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteQuestion (Long questionId) {
		questionService.delete(questionId);
	}

	public Object addQuestion(QuestionRequest qestionRequest, Long pollId) {
		return null;
	}

	public Object editQuestion(@Valid QuestionRequest qestionRequest, Long priceId) {
		return null;
	}

}
