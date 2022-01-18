package com.example.pollsapi.repository;

import com.example.pollsapi.entity.UserPolls;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPollsRepository extends JpaRepository<UserPolls, Long> {
	
}
