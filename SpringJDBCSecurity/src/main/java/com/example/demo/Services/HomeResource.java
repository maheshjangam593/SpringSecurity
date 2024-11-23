package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.AuthenticationRequest;
import com.example.demo.models.AuthenticationResponse;

@RestController
public class HomeResource {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService detailsService;

	@Autowired
	private JwtUtils jwtUtils;

	
	 @GetMapping("/hello") public String home() { return ("<h1> hey hello <h1>");}
	 /* 
	 * @RequestMapping("/user") public String user() { return
	 * ("<h1> hey hello user <h1>");}
	 * 
	 * @RequestMapping("/admin") public String admin() { return
	 * ("<h1> hey hello admin <h1>");}
	 */

	@PostMapping("/authenticate")
	public <T> ResponseEntity<T> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager
					.authenticate((new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword())));

		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect Credentials ", e);
		}

		final UserDetails userDetails = detailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtUtils.generateToken(userDetails);

		return (ResponseEntity<T>) ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
