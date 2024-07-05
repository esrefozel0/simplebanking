package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class WithdrawalTransaction extends Transaction {

  public WithdrawalTransaction(double amount) {
    super(amount);
  }

  @Override
  public void process(Account account) throws InsufficientBalanceException {
    account.withdraw(this.amount);
  }
}