package com.karl.ms10authenticationservice.utils;

import com.karl.ms10authenticationservice.dto.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtils {

    private ResponseUtils() {}

    public static ResponseEntity<ApiResult> generateResponseEntity(
            String uri,
            String message,
            HttpStatus status) {
        return new ResponseEntity<>(
                ApiResult.builder()
                        .path(uri)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .statusCode(status.value())
                        .build(),
                status);
    }
}
