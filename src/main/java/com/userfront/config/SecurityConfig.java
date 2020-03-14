package com.userfront.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.userfront.service.UserServiceImpl.UserSecurityService;

@Configuration //Spring needs to know this is configuration class
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private Environment env;
	
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private static final String SALT="salt"; //will be used to encrypt our password .we dont want to store our password in our database without encrypting them
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(12,new SecureRandom(SALT.getBytes()));
	}


	private static final String[] PUBLIC_MATCHERS = { //A list of of paths we would like to access without using the spring security
			"/webjars/**",
			"/css/**",
			"/js/**",
			"/images/**",
			"/",
			"/about/**",
			"/contact/**",
			"/error/**/*",
			"/console/**",
			"/signup"
	};



	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http //this will authorize any requests coming to PUBLIC_MATCHERS
			.authorizeRequests().
			//antMatchers("/**").
			antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
		
		/*
		    ELI5: What are CSRF-token and how do they help against Cross-Site-Request-Forgery?
			Kinda, yea. Though it's just a random string, not a hash.
			Let's say I have a banking website, which allows you to transfer funds
			from your account to another account. The http request would be something
			equivalent to "please send X funds to account Y".
			Cross site request forgery is when a malicious site creates this request
			and causes an unsuspecting client to send it. If the client happened to be logged in
			to the banking website, the funds will be transferred.
			The solution is to make the transfer a two-step process. In the first step,
			the server generates a random token. The client then needs to reply
			with this token in order to complete the transfer.
			Now, a malicious website can create the first request,
			but it can't see the token, so it can't create the second request
			to complete the transaction.
		 */
		
		http  //This code checks if the user login was successful ,if it's not he hill be redirected to home page with the error and if he logged in then he will have permissions 
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/index?error").defaultSuccessUrl("/userFront").
			loginPage("/index").permitAll()
			.and()
			//handles the logout process
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
//		auth.inMemoryAuthentication().withUser("user").password("password").roles("ROLE_USER");
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
 	
}
