package com.karl.ms5playerservice.exception.handler;

import com.karl.ms5playerservice.dto.ApiResult;
import com.karl.ms5playerservice.exception.ResourceIdRequiredException;
import com.karl.ms5playerservice.exception.ResourceIdShouldBeNullException;
import com.karl.ms5playerservice.exception.ResourceNotFoundException;
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
            ResourceIdShouldBeNullException.class,
            ResourceIdRequiredException.class
    })
    public ResponseEntity<ApiResult> handleException(Exception e,
                                                     HttpServletRequest request) {

        return new ResponseEntity<>(
                ApiResult
                    .builder()
                    .requestURI(request.getRequestURI())
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .timeStamp(LocalDateTime.now())
                    .build(),
                HttpStatus.BAD_REQUEST);
    }

}
