package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.AccountRequestDto;
import com.eteration.simplebanking.dto.AccountResponseDto;
import com.eteration.simplebanking.dto.CreditTransactionDto;
import com.eteration.simplebanking.dto.DebitTransactionDto;
import com.eteration.simplebanking.dto.PhoneBillPaymentTransactionDto;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.services.Interface.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping("/")
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountRequestDto accountRequestDto) {

        AccountResponseDto account = accountService.createAccount(accountRequestDto.getAccountNumber(), accountRequestDto.getOwner());
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable String accountNumber) throws AccountNotFoundException {
        Account account = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(new AccountResponseDto(account));
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody CreditTransactionDto creditTransactionDto)
        throws InsufficientBalanceException, AccountNotFoundException {

        TransactionStatus status = accountService.credit(accountNumber, creditTransactionDto.getAmount());
        return ResponseEntity.ok(status);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody DebitTransactionDto debitTransactionDto)
        throws InsufficientBalanceException, AccountNotFoundException {

        TransactionStatus status = accountService.debit(accountNumber, debitTransactionDto.getAmount());
        return ResponseEntity.ok(status);
    }

    @PostMapping("/phone-bill-payment/{accountNumber}")
    public ResponseEntity<TransactionStatus> phoneBillPayment(@PathVariable String accountNumber, @RequestBody PhoneBillPaymentTransactionDto phoneBillPaymentTransactionDto)
        throws InsufficientBalanceException, AccountNotFoundException {

        TransactionStatus status = accountService.phoneBillPayment(accountNumber, phoneBillPaymentTransactionDto.getAmount());
        return ResponseEntity.ok(status);
    }
}
