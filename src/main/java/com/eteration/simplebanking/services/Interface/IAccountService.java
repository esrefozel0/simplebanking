package com.eteration.simplebanking.services.Interface;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.AccountResponseDto;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;

public interface IAccountService {

  AccountResponseDto createAccount(String accountNumber, String owner);

  TransactionStatus credit(String accountNumber, double amount)
      throws AccountNotFoundException, InsufficientBalanceException;

  TransactionStatus debit(String accountNumber, double amount)
      throws AccountNotFoundException, InsufficientBalanceException;

  Account getAccount(String accountNumber) throws AccountNotFoundException;

  TransactionStatus phoneBillPayment(String accountNumber, double amount)
      throws AccountNotFoundException, InsufficientBalanceException;
}
