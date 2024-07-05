package com.eteration.simplebanking.exception;

import com.eteration.simplebanking.dto.ErrorDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({AccountNotFoundException.class})
  public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException exception) {
    HttpStatus error = HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(
        ErrorDto.builder()
            .withTitle("Account Does Not Exist")
            .withDetail(new ArrayList<>(List.of(exception.getMessage())))
            .withStatus(error.value())
            .withError(error.getReasonPhrase())
            .withErrorCode(1001)
            .build(),
        error
    );
  }

  @ExceptionHandler({InsufficientBalanceException.class})
  public ResponseEntity<Object> handleInsufficientBalanceException(InsufficientBalanceException exception) {
    HttpStatus error = HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(
        ErrorDto.builder()
            .withTitle("Insufficient Balance For Account")
            .withDetail(new ArrayList<>(List.of(exception.getMessage())))
            .withStatus(error.value())
            .withError(error.getReasonPhrase())
            .withErrorCode(1002)
            .build(),
        error
    );
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(exception.getMessage());
  }
}