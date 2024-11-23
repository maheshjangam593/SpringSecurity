package com.example.WithoutConfigurerAdaptor.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.WithoutConfigurerAdaptor.UserInfo;
import com.example.WithoutConfigurerAdaptor.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> user = repository.findByName(username);

		return user.map(UserInfoUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with name " + username));

	}

	public String addUserToDB(UserInfo user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repository.save(user);

		return "User Added Succefully";

	}

}
