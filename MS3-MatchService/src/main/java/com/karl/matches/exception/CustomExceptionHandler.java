package com.karl.matches.exception;

import com.karl.matches.exception.custom.*;
import com.karl.matches.dto.v1.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            ResourceIdRequiredException.class,
            ResourceIdShouldBeNullException.class,
            MatchCreateException.class,
            ResourceNotFoundException.class,
            ResourceExistsException.class,
            DtoToEntityMappingException.class})
    public ResponseEntity<ApiResult> handleException(Exception e,
                                                     HttpServletRequest request) {
        ApiResult apiResult = new ApiResult(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiResult,HttpStatus.BAD_REQUEST);
    }
}
