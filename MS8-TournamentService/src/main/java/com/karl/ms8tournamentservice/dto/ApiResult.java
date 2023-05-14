package com.karl.ms8tournamentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResult {

    String path;
    String message;
    int statusCode;
    LocalDateTime timestamp;
}