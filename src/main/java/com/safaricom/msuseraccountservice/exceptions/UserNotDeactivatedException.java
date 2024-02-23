package com.safaricom.msuseraccountservice.exceptions;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public class UserNotDeactivatedException extends RuntimeJsonMappingException {

    public UserNotDeactivatedException(Long userid) {
        super("User with ID " + userid + " must first be deactivated");
    }

}
