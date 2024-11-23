package com.example.WithoutConfigurerAdaptor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.WithoutConfigurerAdaptor.service.UserInfoUserDetailsService;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SpringSecurityConfigurationWithoutConfigurer {

	@Bean
	protected UserDetailsService userDetailsService() {
		
		
		/*
		 * List<UserDetails> users = new ArrayList<>();
		 * 
		 * UserDetails user =
		 * User.builder().username("user").password(encoder.encode("user")).roles(
		 * "USER", "ADMIN") .build(); UserDetails admin =
		 * User.builder().username("admin").password(encoder.encode("admin")).roles(
		 * "ADMIN").build();
		 * 
		 * return new InMemoryUserDetailsManager(user, admin);
		 */
		return new UserInfoUserDetailsService();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/addUser","/name").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/admin").authenticated().and().authorizeHttpRequests()
				.requestMatchers("/user").authenticated().and().formLogin().and().build();

		// declares which Page(URL) will have What access type
		/*
		 * http.csrf().disable().authorizeHttpRequests()
		 * .requestMatchers("/name").permitAll()
		 * .requestMatchers("/login").authenticated()
		 * .requestMatchers("/admin").hasAuthority("ADMIN")
		 * .requestMatchers("/emp").hasAuthority("EMPLOYEE")
		 * .requestMatchers("/mgr").hasAuthority("MANAGER")
		 * .requestMatchers("/common").hasAnyAuthority("EMPLOYEE","MANAGER")
		 * 
		 * // Any other URLs which are not configured in above antMatchers // generally
		 * declared authenticated() in real time .anyRequest().authenticated()
		 * 
		 * // Login Form Details .and() .formLogin() .defaultSuccessUrl("/welcome",
		 * true)
		 * 
		 * 
		 * // Logout Form Details .and() .logout() .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout"))
		 * 
		 * // Exception Details .and() .exceptionHandling()
		 * .accessDeniedPage("/accessDenied");
		 * 
		 * return http.build();
		 */
	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider  authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return daoAuthenticationProvider;
	}
}
