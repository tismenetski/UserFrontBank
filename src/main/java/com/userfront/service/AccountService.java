package com.userfront.service;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.SavingsTransaction;

import java.security.Principal;

public interface AccountService {
	
	PrimaryAccount createPrimaryAccount();
	SavingsAccount createSavingsAccount();
	void deposit(String accountType, Double amount, Principal principal);
	void withdraw(String accountType, Double amount, Principal principal);


	
	

}
