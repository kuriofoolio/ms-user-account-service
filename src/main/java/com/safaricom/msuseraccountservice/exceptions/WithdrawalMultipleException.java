package com.safaricom.msuseraccountservice.exceptions;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public class WithdrawalMultipleException extends RuntimeJsonMappingException {

    public WithdrawalMultipleException() {
        super("Withdrawal amount must be a multiple of 100");
    }

}
