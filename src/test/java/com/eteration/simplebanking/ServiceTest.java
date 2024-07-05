package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.AccountResponseDto;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.services.Impl.AccountService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class ServiceTest {

  @InjectMocks
  private AccountService accountService;

  @Mock
  private AccountRepository accountRepository;

  private Account account;
  private String accountNumber;
  private String accountName;

  @BeforeEach
  void setUp() {
    accountNumber = "12345";
    accountName = "Kerem Karaca";
    account = new Account(accountName, accountNumber);
  }

  @Test
  void testCreateAccount() {

    AccountResponseDto accountResponseDto = accountService.createAccount(accountNumber, accountName);

    assertNotNull(accountResponseDto);
    assertEquals(account.getAccountNumber(), accountResponseDto.getAccountNumber());
    assertEquals(account.getOwner(), accountResponseDto.getOwner());
  }

  @Test
  void testGetAccount() throws AccountNotFoundException {

    doReturn(Optional.of(account)).when(accountRepository).findByAccountNumber( accountNumber);

    Account foundAccount = accountService.getAccount(accountNumber);

    assertEquals(account, foundAccount);
    verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
  }

  @Test
  void testCredit() throws AccountNotFoundException, InsufficientBalanceException {
    doReturn(Optional.of(account)).when(accountRepository).findByAccountNumber( accountNumber);

    TransactionStatus status = accountService.credit(accountNumber, 100.0);

    assertEquals("OK", status.getStatus());
    assertNotNull(status.getApprovalCode());
    assertEquals(account.getBalance(), 100.0);
    verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
  }

  @Test
  void testDebit() throws AccountNotFoundException, InsufficientBalanceException {
    doReturn(Optional.of(account)).when(accountRepository).findByAccountNumber( accountNumber);

    accountService.credit(accountNumber, 100.0);
    TransactionStatus status = accountService.debit(accountNumber, 50.55);

    assertEquals("OK", status.getStatus());
    assertNotNull(status.getApprovalCode());
    assertEquals(account.getBalance(), 49.45);
    verify(accountRepository, times(2)).findByAccountNumber(accountNumber);
  }

  @Test
  void testPhoneBillPayment() throws AccountNotFoundException, InsufficientBalanceException {
    doReturn(Optional.of(account)).when(accountRepository).findByAccountNumber( accountNumber);

    accountService.credit(accountNumber, 100.0);
    TransactionStatus status = accountService.phoneBillPayment(accountNumber, 50.55);

    assertEquals("OK", status.getStatus());
    assertNotNull(status.getApprovalCode());
    assertEquals(account.getBalance(), 49.45);
    verify(accountRepository, times(2)).findByAccountNumber(accountNumber);
  }

  @Test
  void testDebitInsufficientBalanceException() {
    Assertions.assertThrows( InsufficientBalanceException.class, () -> {
      doReturn(Optional.of(account)).when(accountRepository).findByAccountNumber(accountNumber);
      accountService.debit(accountNumber, 50.55);
    });
  }

  @Test
  void testAccountNotFoundException() {
    Assertions.assertThrows( AccountNotFoundException.class, () -> {
      doReturn(Optional.empty()).when(accountRepository).findByAccountNumber(accountNumber);
      accountService.getAccount(accountNumber);
    });
  }

}
