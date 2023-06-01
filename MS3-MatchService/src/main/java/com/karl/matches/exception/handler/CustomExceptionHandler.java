package com.karl.matches.exception.handler;

import com.karl.matches.dto.ApiResult;
import com.karl.matches.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            MissingOrNonExistentFieldException.class,
            FeignClientUnavailableException.class,
            ResourceIdRequiredException.class,
            ResourceIdShouldBeNullException.class,
            MatchCreateException.class,
            ResourceNotFoundException.class,
            DtoToEntityMappingException.class})
    public ResponseEntity<ApiResult> handleException(Exception e,
                                                     HttpServletRequest request) {
        ApiResult apiResult = new ApiResult(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e instanceof FeignClientUnavailableException)
            status = HttpStatus.SERVICE_UNAVAILABLE;

        if (e instanceof MissingOrNonExistentFieldException ||
                e instanceof DtoToEntityMappingException)
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(e instanceof ResourceNotFoundException)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(apiResult, status);
    }
}
