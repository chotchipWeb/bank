package com.chotchip.processing.exception;


public class AccountNotFoundException extends AccountWrapperException {
    private static final String MESSAGE = "Account not found";

    public AccountNotFoundException() {
        super(MESSAGE);
    }
}
