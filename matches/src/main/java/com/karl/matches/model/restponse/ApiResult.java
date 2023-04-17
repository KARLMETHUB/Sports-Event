package com.karl.matches.model.restponse;

import java.time.LocalDateTime;

public record ApiResult(
        String requestURI,
        String message,
        int value,
        LocalDateTime now) { }