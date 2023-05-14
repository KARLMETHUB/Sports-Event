package com.karl.ms7ticketservice.exception.handler;

import com.karl.ms7ticketservice.dto.ApiResult;
import com.karl.ms7ticketservice.exception.ResourceNotFoundException;
import com.karl.ms7ticketservice.exception.TicketCreateException;
import com.karl.ms7ticketservice.exception.TicketDeleteException;
import com.karl.ms7ticketservice.exception.TicketUpdateException;
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
            TicketCreateException.class,
            TicketUpdateException.class,
            TicketDeleteException.class
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
