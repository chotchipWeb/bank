package com.chotchip.processing.exception;

public class AccountFlawBalanceException extends AccountWrapperException {
    private static final String MESSAGE = "At account flaw balance";

    public AccountFlawBalanceException() {
        super(MESSAGE);
    }
}
