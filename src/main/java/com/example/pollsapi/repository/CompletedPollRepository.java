package com.example.pollsapi.repository;

import com.example.pollsapi.entity.CompletedPoll;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedPollRepository extends JpaRepository<CompletedPoll, Long> {
	
}
