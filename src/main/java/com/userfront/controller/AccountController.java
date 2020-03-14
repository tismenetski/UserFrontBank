package com.userfront.controller;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.User;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/account") // When defined in class level , it means that every request will append the class requestmapping annotation
public class AccountController {



	@Autowired
	private UserService userService;


	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model, Principal principal)
	{
		User user= userService.findByUsername(principal.getName()); //Create new user and receive it via the userService findByUserName method -> the principal is the user that currently logged in
		PrimaryAccount primaryAccount= user.getPrimaryAccount(); // Create primary account instance and assign it the account of the user
		model.addAttribute("primaryAccount",primaryAccount); // model have two parameters,the model name , and the object that passed to it .
		return "primaryAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount()
	{
		return "savingsAccount";
	}
	
	//@RequestMapping

	/*
	@RequestMapping(value = "/primaryAccount", method = RequestMethod.GET)
	public BigDecimal primaryAccountBalance(Long id,Model model)
	{
		PrimaryAccount primaryAccount=

		model.addAttribute()
	}
	*/
	
	
	
	
}
