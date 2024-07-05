package com.eteration.simplebanking.model;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DepositTransaction extends Transaction {

  public DepositTransaction(double amount) {
    super(amount);
  }

  @Override
  public void process(Account account) {
    account.deposit(this.amount);
  }
}