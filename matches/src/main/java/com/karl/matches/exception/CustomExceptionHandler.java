package com.karl.matches.exception;

import com.karl.matches.exception.custom.ResourceExistsException;
import com.karl.matches.exception.custom.ResourceNotFoundException;
import com.karl.matches.model.restponse.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            ResourceNotFoundException.class,
            ResourceExistsException.class})
    public ResponseEntity<ApiResult> handleException(Exception e,
                                                     HttpServletRequest request) {
        ApiResult apiResult = new ApiResult(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiResult,HttpStatus.NOT_FOUND);
    }
}
