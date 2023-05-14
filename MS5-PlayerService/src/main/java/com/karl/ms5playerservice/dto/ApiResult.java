package com.karl.ms5playerservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResult {

    private String requestURI;
    private String message;
    private int status;
    private LocalDateTime timeStamp;
}
