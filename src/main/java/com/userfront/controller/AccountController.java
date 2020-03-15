package com.userfront.controller;

import com.userfront.domain.*;
import com.userfront.service.AccountService;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account") // When defined in class level , it means that every request will append the class requestmapping annotation
public class AccountController {



	@Autowired
	private UserService userService;

	@Autowired
	private AccountService 	accountService;

	@Autowired
	private TransactionService transactionService;


	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model, Principal principal)
	{
		List<PrimaryTransaction> primaryTransactionList=transactionService.findPrimaryTransactionList(principal.getName());

		User user= userService.findByUsername(principal.getName()); //Create new user and receive it via the userService findByUserName method -> the principal is the user that currently logged in
		PrimaryAccount primaryAccount= user.getPrimaryAccount(); // Create primary account instance and assign it the account of the user
		model.addAttribute("primaryAccount",primaryAccount); // model have two parameters,the model name , and the object that passed to it .
		model.addAttribute("primaryTransactionList",primaryTransactionList);
		return "primaryAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount(Model model, Principal principal)
	{
		List<SavingsTransaction> savingsTransactionList=transactionService.findSavingsTransactionList(principal.getName());

		User user= userService.findByUsername(principal.getName()); //Create new user and receive it via the userService findByUserName method -> the principal is the user that currently logged in
		SavingsAccount savingsAccount= user.getSavingsAccount(); // Create primary account instance and assign it the account of the user
		model.addAttribute("savingsAccount",savingsAccount); // model have two parameters,the model name , and the object
		model.addAttribute("savingsTransactionList",savingsTransactionList);
		return "savingsAccount";
	}


	@RequestMapping(value ="/deposit", method = RequestMethod.GET) //Get will lead to the deposit html page
	public String deposit(Model model)
	{
		model.addAttribute("accountType",""); //variable binded to the model
		model.addAttribute("amount",""); //variable binded to the model

		return "deposit";

	}

	@RequestMapping(value = "/deposit", method = RequestMethod.POST) // here we receive the values of amount and accountType and user them in the accountService deposit method
	public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal)
	{
		accountService.deposit(accountType,Double.parseDouble(amount),principal);

		return "redirect:/userFront";
	}




	@RequestMapping(value ="/withdraw", method = RequestMethod.GET) //Get will lead to the deposit html page
	public String withdraw(Model model)
	{
		model.addAttribute("accountType",""); //variable binded to the model
		model.addAttribute("amount",""); //variable binded to the model

		return "withdraw";

	}

	@RequestMapping(value = "/withdraw", method = RequestMethod.POST) // here we receive the values of amount and accountType and user them in the accountService deposit method
	public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal)
	{
		accountService.withdraw(accountType,Double.parseDouble(amount),principal);

		return "redirect:/userFront";
	}
	
	
	
	
}
