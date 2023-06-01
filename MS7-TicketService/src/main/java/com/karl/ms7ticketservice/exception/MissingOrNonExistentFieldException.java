package com.karl.ms7ticketservice.exception;

public class MissingOrNonExistentFieldException extends Exception {
    public MissingOrNonExistentFieldException(String message) {
        super(message);
    }
}
