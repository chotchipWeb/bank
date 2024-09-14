package com.chotchip.processing.exception;

public class AccountNotEqualsUUIDException extends AccountWrapperException{
    private static final String MESSAGE = "UUID not equals";
    public AccountNotEqualsUUIDException(){
        super(MESSAGE);
    }
}
