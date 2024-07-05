package com.eteration.simplebanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreditTransactionDto {

  private double amount;
}
