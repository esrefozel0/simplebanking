package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.model.Account;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDto {

  private String owner;
  private String accountNumber;
  private double balance;
  private LocalDateTime createDate;
  private List<TransactionDto> transactions;

  public AccountResponseDto(Account account) {
    this.owner = account.getOwner();
    this.accountNumber = account.getAccountNumber();
    this.balance = account.getBalance();
    this.createDate = account.getCreatedAt();
    this.transactions = account.getTransactions().stream().map(TransactionDto::new).toList();
  }

}
