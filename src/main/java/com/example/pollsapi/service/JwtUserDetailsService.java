package com.example.pollsapi.service;

import java.util.Optional;

import com.example.pollsapi.entity.User;
import com.example.pollsapi.exception.ResourceNotFoundException;
import com.example.pollsapi.repository.UserRepository;
import com.example.pollsapi.security.JwtUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public JwtUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + username));
	}

	public UserDetails loadUserById(Long id)  {

		Optional<User> user = userRepository.findById(id);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new ResourceNotFoundException("Ошибка авторизации. Пользователя", "параметром id", id));

	} 

}
