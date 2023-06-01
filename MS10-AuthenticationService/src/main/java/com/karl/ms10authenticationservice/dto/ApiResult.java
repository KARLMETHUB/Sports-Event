package com.karl.ms10authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiResult {

    String path;
    String message;
    int statusCode;
    LocalDateTime timestamp;
}