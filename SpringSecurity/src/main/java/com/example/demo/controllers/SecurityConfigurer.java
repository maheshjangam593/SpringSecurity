package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import com.examples.demo.services.CustomAccessDecisionVoter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private UserDetailsService MyUserDetailService;

	@Resource
	private CustomAccessDecisionVoter accessDecisionVoter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(MyUserDetailService); 
		// custom type authentication
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
				.password("admin").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 /* http.authorizeHttpRequests().antMatchers("/admin").hasRole("ADMIN").
		  antMatchers("/user") .hasAnyRole("USER",
		  "ADMIN").antMatchers("/").permitAll()*/
		 

		http.authorizeHttpRequests().antMatchers("/**").hasRole("USER")
		
				// if we give url matching above it ll navigate to formlogin page
				.and().formLogin()

				// if after successful login it will navigate to success url
				.defaultSuccessUrl("/success", true)
		// if we want to logout from session in sometime and navigate to
		// invalidsessionurl
		/*
		 * .and().sessionManagement() .invalidSessionUrl("/login?invalid-session=true")
		 * .and() .sessionManagement() .maximumSessions(1)
		 * .expiredUrl("/login?invalid-session=true")
		 */;

	}

	@Bean
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new AuthenticatedVoter(),
				new RoleVoter(), new WebExpressionVoter());
		return new UnanimousBased(decisionVoters);
	}

	@Bean
	public PasswordEncoder passwordEncoder()

	{
		return NoOpPasswordEncoder.getInstance();

	}
}
