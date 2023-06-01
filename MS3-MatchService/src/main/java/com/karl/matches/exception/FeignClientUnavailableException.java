package com.karl.matches.exception;

public class FeignClientUnavailableException extends Exception {
    public FeignClientUnavailableException(String message) {
        super(message);
    }
}
