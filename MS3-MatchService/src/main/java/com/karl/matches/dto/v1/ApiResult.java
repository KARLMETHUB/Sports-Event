package com.karl.matches.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/*
public record ApiResult(
        String requestURI,
        String message,
        int value,
        LocalDateTime now) { }*/


@Data
@AllArgsConstructor
public class ApiResult {

    private String requestURI;
    private String message;
    private int value;
    private LocalDateTime timeStamp;
}