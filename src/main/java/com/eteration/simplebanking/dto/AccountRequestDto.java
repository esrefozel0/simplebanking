package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.model.Account;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountRequestDto {

  private String owner;
  private String accountNumber;

}
