package com.eteration.simplebanking.services.Impl;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.AccountResponseDto;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.Interface.IAccountService;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import com.eteration.simplebanking.repository.AccountRepository;
import java.util.UUID;
import java.util.Optional;
@Service
public class AccountService implements IAccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public AccountResponseDto createAccount(String accountNumber, String owner) {
    Account account = new Account(owner, accountNumber);
    accountRepository.save(account);
    return new AccountResponseDto(account);
  }

  @Transactional
  public TransactionStatus credit(String accountNumber, double amount)
      throws AccountNotFoundException, InsufficientBalanceException {

    DepositTransaction transaction = new DepositTransaction(amount);
    return processTransaction(accountNumber, transaction);
  }

  @Transactional
  public TransactionStatus debit(String accountNumber, double amount)
      throws AccountNotFoundException, InsufficientBalanceException {

    WithdrawalTransaction transaction = new WithdrawalTransaction(amount);
    return processTransaction(accountNumber, transaction);
  }

  @Transactional
  public TransactionStatus phoneBillPayment(String accountNumber, double amount)
      throws AccountNotFoundException, InsufficientBalanceException {

    PhoneBillPaymentTransaction transaction = new PhoneBillPaymentTransaction(amount);
    return processTransaction(accountNumber, transaction);
  }

  public TransactionStatus processTransaction(String accountNumber, Transaction transaction)
      throws AccountNotFoundException, InsufficientBalanceException {

    Account account = getAccount(accountNumber);
    UUID approvalCode = UUID.randomUUID();
    transaction.setApprovalCode(approvalCode);
    account.post(transaction);
    return new TransactionStatus("OK", approvalCode.toString());
  }

  public Account getAccount(String accountNumber) throws AccountNotFoundException {
    Optional<Account> accountOpt = findAccount(accountNumber);
    if (accountOpt.isEmpty()) {
      throw new AccountNotFoundException("Account not found");
    }
    return accountOpt.get();
  }

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  public Optional<Account> findAccount(String accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber);
  }
}