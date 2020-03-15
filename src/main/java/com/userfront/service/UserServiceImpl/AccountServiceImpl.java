package com.userfront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;


import com.userfront.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static int nextAccountNumber = 11223145;
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private UserService userService;
	
	public PrimaryAccount createPrimaryAccount()
	{
		PrimaryAccount primaryAccount=new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAccountNumber(accountGen());
		
		primaryAccountDao.save(primaryAccount);
		
		return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
	}
	
	public SavingsAccount createSavingsAccount()
	{
		SavingsAccount savingsAccount=new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());
		
		savingsAccountDao.save(savingsAccount);
		
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
	}
	
	private int accountGen()
	{
		return ++nextAccountNumber;
	}


	public void deposit(String accountType, Double amount, Principal principal)
	{
		User user = userService.findByUsername(principal.getName()); // new instance of user using the userService

		if (accountType.equalsIgnoreCase("Primary")) // check if the account type is of type "Primary"
		{
			PrimaryAccount primaryAccount = new PrimaryAccount(); // new instance of PrimaryAccount
			primaryAccount = user.getPrimaryAccount(); // equals the account of the given user(principal)
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount))); // add funds to the specific account
			primaryAccountDao.save(primaryAccount); //save the primaryAccount in the Database
			Date date  = new Date();
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,"Deposit to primary Account","Account","Finished",amount,primaryAccount.getAccountBalance(),primaryAccount);
		}
		else if (accountType.equalsIgnoreCase("Savings"))
		{
			SavingsAccount savingsAccount  = new SavingsAccount(); // new instance of SavingsAccount
			savingsAccount = user.getSavingsAccount(); // equals the account of the given user(principal)
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount))); // add funds to the specific account
			savingsAccountDao.save(savingsAccount); //save the savingsAccount in the Database
			Date date  = new Date();
			SavingsTransaction savingsTransaction = new SavingsTransaction(date,"Deposit to savings Account","Account","Finished",amount,savingsAccount.getAccountBalance(),savingsAccount);
		}

	}
	

}
