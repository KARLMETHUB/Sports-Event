package com.karl.ms6fieldservice.exception.handler;

import com.karl.ms6fieldservice.dto.ApiResult;
import com.karl.ms6fieldservice.exception.FieldCreateException;
import com.karl.ms6fieldservice.exception.FieldDeleteException;
import com.karl.ms6fieldservice.exception.FieldUpdateException;
import com.karl.ms6fieldservice.exception.ResourceNotFoundException;
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
            FieldCreateException.class,
            FieldUpdateException.class,
            FieldDeleteException.class
    })
    public ResponseEntity<ApiResult> handleException(Exception e,
                                                     HttpServletRequest request) {
        ApiResult apiResult = new ApiResult(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiResult,HttpStatus.BAD_REQUEST);
    }
}
