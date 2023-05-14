package com.karl.ms8tournamentservice.exceptions.handler;


import com.karl.ms8tournamentservice.dto.ApiResult;
import com.karl.ms8tournamentservice.exceptions.*;
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
            TournamentCreateException.class,
            TournamentUpdateException.class,
            TournamentDeleteException.class
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
