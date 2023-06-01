package com.karl.ms10authenticationservice.controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.karl.ms10authenticationservice.dto.ApiResult;
import com.karl.ms10authenticationservice.dto.AuthRequest;
import com.karl.ms10authenticationservice.dto.UserCredentialDTO;
import com.karl.ms10authenticationservice.dto.UserCredentialDTONoPassword;
import com.karl.ms10authenticationservice.entity.UserCredential;
import com.karl.ms10authenticationservice.exception.InvalidTokenException;
import com.karl.ms10authenticationservice.exception.LoginException;
import com.karl.ms10authenticationservice.exception.UserRegistrationException;
import com.karl.ms10authenticationservice.service.AuthService;
import com.karl.ms10authenticationservice.utils.UserCredentialDtoConverter;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.security.SignatureException;
import java.time.LocalDateTime;

import static com.karl.ms10authenticationservice.constants.ExceptionMessages.*;
import static com.karl.ms10authenticationservice.constants.ResultMessages.*;
import static com.karl.ms10authenticationservice.utils.ResponseUtils.generateResponseEntity;

@RestController
@RequestMapping("/")
//@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResult> addNewUser(
            HttpServletRequest request,
            @RequestBody UserCredentialDTO user)
            throws UserRegistrationException {

        UserCredentialDTONoPassword createdUser = UserCredentialDtoConverter
                .toDtoNoPassword(authService.saveUser(user));

        return generateResponseEntity(
                request.getRequestURI(),
                String.format(REGISTRATION_SUCCESS,createdUser.getUserName()),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResult> getToken(
            HttpServletRequest request,
            @RequestBody AuthRequest authRequest) throws LoginException {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (!authenticate.isAuthenticated())
            throw new LoginException(LOGIN_FAILED);

        return generateResponseEntity(
                    request.getRequestURI(),
                    authService.generateToken(authRequest.getUsername()),
                    HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResult> validateToken(
            HttpServletRequest request,
            @RequestParam("token") String token) {

        try {
            authService.validateToken(token);
            return generateResponseEntity(request.getRequestURI(),
                    TOKEN_VALID,
                    HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException(TOKEN_EXPIRED);
        }
    }
}