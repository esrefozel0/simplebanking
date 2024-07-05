package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PhoneBillPaymentTransaction extends Transaction {

  public PhoneBillPaymentTransaction(double amount) {
    super(amount);
  }

  @Override
  public void process(Account account) throws InsufficientBalanceException {
    account.withdraw(this.amount);
  }
}