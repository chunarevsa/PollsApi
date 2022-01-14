package com.example.pollsapi.service;

import java.util.Set;

import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.payload.EditPollRequest;
import com.example.pollsapi.payload.PollRequest;

public interface PollInterface {

	public Set<Poll> getPolls();
	
	public Poll addPoll(PollRequest pollRequest);

	public Poll editPoll(Long id, EditPollRequest editPollRequest);

	public Object start();

}
