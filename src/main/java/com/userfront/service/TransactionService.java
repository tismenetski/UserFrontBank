package com.userfront.service;

import com.userfront.domain.*;

import java.security.Principal;
import java.util.List;

public interface TransactionService {

    List<PrimaryTransaction> findPrimaryTransactionList(String username);
    List<SavingsTransaction> findSavingsTransactionList(String username);
    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);
    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);
    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception;
    void deleteRecipientByName(String recipientName);
    Recipient findRecipientByName(String recipientName);
    Recipient saveRecipient(Recipient recipient);
    List<Recipient> findRecipientList(Principal principal);
    void toSomeoneElseTransfer(Recipient recipient,String accountType,String amount,PrimaryAccount primaryAccount,SavingsAccount savingsAccount);


}
