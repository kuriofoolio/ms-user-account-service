package com.safaricom.msuseraccountservice.exceptions;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public class UserNotActiveException extends RuntimeJsonMappingException {
    public UserNotActiveException(Long userid) {
        super("User with ID " + userid + " not active");
    }

}
