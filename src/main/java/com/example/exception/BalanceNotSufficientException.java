package com.example.exception;

public class BalanceNotSufficientException extends RuntimeException{
    public BalanceNotSufficientException(String msg){
        super(msg);
    }
}
