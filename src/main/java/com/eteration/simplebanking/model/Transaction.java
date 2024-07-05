package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "transaction")
public abstract class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "amount", nullable = false, updatable = false)
  protected double amount;

  @Setter
  @Column(name = "approval_code", nullable = false, updatable = false)
  protected UUID approvalCode;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "dtype", insertable = false, updatable = false)
  private String dtype;

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "account_id",
      foreignKey = @ForeignKey(name = "transaction_account_fk"),
      nullable = false
  )
  @ToString.Exclude
  protected Account account;


  public Transaction(double amount) {
    this.amount = amount;
  }

  public abstract void process(Account account) throws InsufficientBalanceException;


}