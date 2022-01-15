package com.example.pollsapi.service;

import java.util.Optional;
import java.util.Set;

import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.payload.PollRequest;

public interface PollServiceInterface {

	public Set<Poll> getPolls();
	
	public Optional<Poll> addPoll(PollRequest pollRequest);

	public Optional<Poll> editPoll(Long id, PollRequest pollRequest);

	public Object start();

}
