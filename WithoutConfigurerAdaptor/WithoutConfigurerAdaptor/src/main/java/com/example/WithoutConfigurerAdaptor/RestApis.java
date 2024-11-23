package com.example.WithoutConfigurerAdaptor;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.WithoutConfigurerAdaptor.service.UserInfoUserDetailsService;

@RestController
public class RestApis {

	@Autowired
	private UserInfoUserDetailsService detailsService;

	@GetMapping("/name")
	public ResponseEntity<String> getName() {
		int[] ar = { 1, 2, 34 };
		Arrays.stream(ar).forEach(System.out::println);
		return ResponseEntity.ok("Hello world");
	}

	@GetMapping("/welcome")
	public ResponseEntity<String> login() {
		return ResponseEntity.ok("Logged in successfully");
	}

	@PostMapping("/addUser")
	public ResponseEntity<String> addUser(@RequestBody UserInfo user) {

		String response = detailsService.addUserToDB(user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public ResponseEntity<String> user() {
		return ResponseEntity.ok("user logged in successfully");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> admin() {
		return ResponseEntity.ok(" Admin Logged in successfully");
	}
}
