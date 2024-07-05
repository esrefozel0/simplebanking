package com.eteration.simplebanking.exception;

public class AccountNotFoundException extends Exception{
  public AccountNotFoundException(String message){
    super(message);
  }
}