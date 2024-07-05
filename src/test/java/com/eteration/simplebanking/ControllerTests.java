package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.AccountRequestDto;
import com.eteration.simplebanking.dto.AccountResponseDto;
import com.eteration.simplebanking.dto.CreditTransactionDto;
import com.eteration.simplebanking.dto.DebitTransactionDto;
import com.eteration.simplebanking.dto.PhoneBillPaymentTransactionDto;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.services.Impl.AccountService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {

    @Spy
    @InjectMocks
    private AccountController controller;
 
    @Mock
    private AccountService service;

    private TransactionStatus transactionStatus;
    private TransactionStatus transactionStatus2;
    private Account account;


    @BeforeEach
    void setUp() {
        transactionStatus = new TransactionStatus("OK", "1");
        transactionStatus2 = new TransactionStatus("OK", "2");
        account = new Account("Kerem Karaca","123456");

    }
    
    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {

        doReturn(transactionStatus).when(service).credit(account.getAccountNumber(), 1000.0);
        ResponseEntity<TransactionStatus> result = controller.credit( account.getAccountNumber(), new CreditTransactionDto(1000.0));
        verify(service, times(1)).credit(account.getAccountNumber(), 1000.0);
        assertNotNull(result.getBody());
        assertEquals(transactionStatus.getStatus(), result.getBody().getStatus());
    }

    @Test
    public void givenId_CreateAccount_thenReturnJson() {
        doReturn(new AccountResponseDto(account)).when(service).createAccount(
            account.getAccountNumber(), account.getOwner());
        ResponseEntity<AccountResponseDto> result = controller.createAccount(new AccountRequestDto(account.getOwner(), account.getAccountNumber()));
        verify(service, times(1)).createAccount(account.getAccountNumber(), account.getOwner());
        assertNotNull(result.getBody());;
        assertEquals(account.getAccountNumber(), result.getBody().getAccountNumber());
        assertEquals(account.getOwner(), result.getBody().getOwner());
    }

    @Test
    public void givenId_Debit_thenReturnJson()
        throws Exception {

        doReturn(transactionStatus).when(service).debit( account.getAccountNumber(), 1000.0);
        ResponseEntity<TransactionStatus> result = controller.debit( account.getAccountNumber(), new DebitTransactionDto(1000.0));
        verify(service, times(1)).debit(account.getAccountNumber(), 1000.0);
        assertNotNull(result.getBody());
        assertEquals(transactionStatus.getStatus(), result.getBody().getStatus());
    }

    @Test
    public void givenId_PhoneBillPayment_thenReturnJson()
        throws Exception {

        doReturn(transactionStatus).when(service).phoneBillPayment( account.getAccountNumber(), 1000.0);
        ResponseEntity<TransactionStatus> result = controller.phoneBillPayment( account.getAccountNumber(), new PhoneBillPaymentTransactionDto(1000.0));
        verify(service, times(1)).phoneBillPayment(account.getAccountNumber(), 1000.0);
        assertNotNull(result.getBody());
        assertEquals(transactionStatus.getStatus(), result.getBody().getStatus());
    }


    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {

        doReturn(transactionStatus).when(service).credit( account.getAccountNumber(), 1000.0);
        doReturn(transactionStatus2).when(service).debit( account.getAccountNumber(), 50.0);

        ResponseEntity<TransactionStatus> result = controller.credit( account.getAccountNumber(), new CreditTransactionDto(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit( account.getAccountNumber(), new DebitTransactionDto(50.0));

        assertNotNull(result.getBody());
        assertNotNull(result2.getBody());
        assertEquals(transactionStatus.getStatus(), result.getBody().getStatus());
        assertEquals(transactionStatus2.getStatus(), result2.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {

            doThrow(new InsufficientBalanceException("")).when(service).credit( account.getAccountNumber(), 1000.0);
            controller.credit( account.getAccountNumber(), new CreditTransactionDto(1000.0));
        });
    }


    @Test
    public void givenId_GetAccount_thenReturnJson()
    throws Exception {

        doReturn(account).when(service).getAccount( account.getAccountNumber());
        ResponseEntity<AccountResponseDto> result = controller.getAccount( account.getAccountNumber());
        verify(service, times(1)).getAccount(account.getAccountNumber());
        assertNotNull(result.getBody());
        assertEquals(account.getAccountNumber(), result.getBody().getAccountNumber());
        assertEquals(account.getOwner(), result.getBody().getOwner());
    }

}
