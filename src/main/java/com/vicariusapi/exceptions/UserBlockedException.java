package com.vicariusapi.exceptions;

import org.apache.coyote.BadRequestException;

public class UserBlockedException extends BadRequestException {

    public UserBlockedException(String message) {
        super(message);
    }
}
