package com.safaricom.msuseraccountservice.exceptions;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public class UserNotFoundException extends RuntimeJsonMappingException {

    public UserNotFoundException(Long userid) {
        super("User with ID " + userid + " not found in the database");
    }
}
