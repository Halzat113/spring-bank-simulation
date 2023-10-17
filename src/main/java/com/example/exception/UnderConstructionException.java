package com.example.exception;

public class UnderConstructionException extends RuntimeException{
    public UnderConstructionException(String msg) {
        super(msg);
    }
}
