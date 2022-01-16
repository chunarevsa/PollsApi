package com.example.pollsapi.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.payload.PollRequest;

public interface PollServiceInterface {

	public List<Poll> getPolls();
	
	public Optional<Poll> addPoll(PollRequest pollRequest) throws ParseException;

	public Optional<Poll> editPoll(Long id, PollRequest pollRequest) throws ParseException;

	public Object start();

}
