package com.example.demo.Services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * 
 * @author 91846
 *
 */

@Service
public class JwtUtils {

	private String SECRET_KEY = "secret";

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {	
		  final Claims claims = extractAllClaims(token); return
		  claimResolver.apply(claims); 
	}

	private Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiraion(token).before(new Date());
	}

	private Date extractExpiraion(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, userDetails.getUsername());
		// return Jwts.builder().setClaims().(UserDetails).set
	}

	private String createToken(Map<String, Object> claims, String username) {
		
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userDetails.getUsername().equals(userName) && !isTokenExpired(token));

	}
}
