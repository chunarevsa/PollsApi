package com.example.pollsapi.repository;

import java.util.Set;

import com.example.pollsapi.entity.Poll;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
    
    Set<Poll> findAllByActive(boolean active);
}
