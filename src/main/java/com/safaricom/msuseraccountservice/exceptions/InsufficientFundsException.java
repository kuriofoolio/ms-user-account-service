package com.safaricom.msuseraccountservice.exceptions;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public class InsufficientFundsException extends RuntimeJsonMappingException {

    public InsufficientFundsException() {
        super("Insufficient funds");
    }

}
