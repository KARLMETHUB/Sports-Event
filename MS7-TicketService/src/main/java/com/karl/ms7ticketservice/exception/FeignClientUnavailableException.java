package com.karl.ms7ticketservice.exception;

public class FeignClientUnavailableException extends Exception {
    public FeignClientUnavailableException(String message) {
        super(message);
    }
}
