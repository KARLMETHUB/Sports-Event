package com.karl.ms2apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private RouteValidator() {}

    public static final List<String> openApiEndpoints = List.of(
            "register",
            "login",
            /*For debugging purposes only*/
            /*"auth/validate",*/
            /*"/validate",*/
            "/eureka"
    );

    public static final Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}