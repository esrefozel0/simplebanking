package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "owner", nullable = false)
  private String owner;

  @Column(name = "account_number", nullable = false, unique = true)
  private String accountNumber;

  @Column(name = "balance", nullable = false)
  private double balance;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
  private List<Transaction> transactions = new ArrayList<>();

  public Account(String owner, String accountNumber) {
    this.owner = owner;
    this.accountNumber = accountNumber;
    this.balance = 0;
  }

  // Process a generic transaction
  public void post(Transaction transaction) throws InsufficientBalanceException {
    transaction.process(this);
    transaction.account = this;
    transactions.add(transaction);
  }

  public void deposit(double amount)  {
    double newBalance = this.getBalance() + amount;
    this.setBalance(newBalance);
  }

  public void withdraw(double amount) throws InsufficientBalanceException {
    double newBalance = this.getBalance() - amount;
    if (newBalance < 0) {
      throw new InsufficientBalanceException("Insufficient balance for withdrawal");
    }
    this.setBalance(newBalance);
  }

}