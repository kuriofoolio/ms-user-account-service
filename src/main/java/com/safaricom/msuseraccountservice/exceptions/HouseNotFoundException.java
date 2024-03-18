package com.safaricom.msuseraccountservice.exceptions;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public class HouseNotFoundException extends RuntimeJsonMappingException {

    public HouseNotFoundException(Long houseid) {
        super("House with ID " + houseid + " not found in the database");
    }
}
