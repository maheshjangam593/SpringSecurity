package com.example.demo.Services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.models.JwtRequestFilters;

@EnableWebSecurity
@EnableWebMvc
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource datasource;
	
	public static final String[] PUBLIC_URLS= {"/authenticate","/v3/api-docs","/v2/api-docs","/swagger-resources/**","/swagger-ui/**","/webjars/**","/hello"};

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	JwtRequestFilters jwtRequestFilter;

//Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// custom type authentication
		// auth.userDetailsService(MyUserDetailService);
		// in memoryAuthentication
//		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
//				.password("admin").roles("ADMIN");

		// JDBCAuthentication

		// with out sql database uses default schema
//		auth.jdbcAuthentication().dataSource(datasource).withDefaultSchema()
//				.withUser(User.withUsername("user").password("user").roles("USER"))
//				.withUser(User.withUsername("admin").password("admin").roles("ADMIN"));

		// auth.jdbcAuthentication().dataSource(datasource).usersByUsernameQuery(null).authoritiesByUsernameQuery(null);
		/*
		 * auth.jdbcAuthentication().dataSource(datasource)
		 * .usersByUsernameQuery("select username,password,enabled from users where username= ?"
		 * )
		 * .authoritiesByUsernameQuery("select username,authority from authorities where username=?"
		 * );
		 */

		// this below code is for JWTToken.
		// authentication
		auth.userDetailsService(myUserDetailsService);

	}

	// Authorization
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated()

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

// Authorization
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.authorizeHttpRequests().antMatchers("/admin").hasRole("ADMIN").
	 * antMatchers("/user") .hasAnyRole("USER",
	 * "ADMIN").antMatchers("/").permitAll().and().formLogin(); }
	 */

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder()

	{
		return NoOpPasswordEncoder.getInstance();

	}
}
