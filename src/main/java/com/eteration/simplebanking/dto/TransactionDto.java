package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.model.Transaction;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

  private LocalDateTime date;
  private double amount;
  private String type;
  private String approvalCode;

  public TransactionDto(Transaction transaction) {
    this.date = transaction.getCreatedAt();
    this.amount = transaction.getAmount();
    this.type = transaction.getDtype();
    this.approvalCode = transaction.getApprovalCode().toString();
  }

}
