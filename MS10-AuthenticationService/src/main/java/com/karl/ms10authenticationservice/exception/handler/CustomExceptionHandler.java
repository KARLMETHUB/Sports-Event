package com.karl.ms10authenticationservice.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.karl.ms10authenticationservice.dto.ApiResult;
import com.karl.ms10authenticationservice.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            JsonParseException.class,
            ExpiredJwtException.class,
            LoginException.class,
            InvalidTokenException.class,
            MissingHeaderException.class,
            UserRegistrationException.class,
            ResourceNotFoundException.class})
    public ResponseEntity<ApiResult> handleException(Exception e,
                                                     HttpServletRequest request) {
        ApiResult apiResult = new ApiResult(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e instanceof UserRegistrationException ||
            e instanceof JsonParseException ||
            e instanceof ExpiredJwtException)
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(e instanceof ResourceNotFoundException)
            status = HttpStatus.NOT_FOUND;

        if(e instanceof InvalidTokenException ||
            e instanceof LoginException)
            status = HttpStatus.FORBIDDEN;

        return new ResponseEntity<>(apiResult, status);
    }
}
