package com.example.pollsapi.service;

import java.util.Set;

import javax.validation.Valid;

import com.example.pollsapi.entity.Poll;
import com.example.pollsapi.payload.EditPollRequest;
import com.example.pollsapi.payload.PollRequest;

public class PollService {

	public Object addItem(@Valid PollRequest pollRequest) {
		return null;
	}

	public Object editPoll(Long id, @Valid EditPollRequest editPollRequest) {
		return null;
	}

	public void deletePoll(Long id) {
	}

	public Object getPolls() {
		return null;
	}

}
