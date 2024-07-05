package com.eteration.simplebanking.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionStatus {
  private String status;
  private String approvalCode;

}