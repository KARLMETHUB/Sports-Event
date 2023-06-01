package com.karl.teams.exceptions.handler;

import com.karl.teams.exceptions.PlayerAssignmentException;
import com.karl.teams.exceptions.ResourceIdRequiredException;
import com.karl.teams.exceptions.ResourceIdShouldBeNullException;
import com.karl.teams.exceptions.ResourceNotFoundException;
import com.karl.teams.model.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            PlayerAssignmentException.class,
            ResourceNotFoundException.class,
            ResourceIdShouldBeNullException.class,
            ResourceIdRequiredException.class})
    public ResponseEntity<ApiResult> handleException(Exception e,
                                                     HttpServletRequest request) {
        ApiResult apiResult = new ApiResult(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiResult,
                e instanceof ResourceNotFoundException ?
                HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST);
    }



}
