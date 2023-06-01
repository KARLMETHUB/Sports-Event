package com.karl.ms10authenticationservice.service;

import com.google.common.base.Strings;
import com.karl.ms10authenticationservice.config.PatternsConfig;
import com.karl.ms10authenticationservice.dto.UserCredentialDTO;
import com.karl.ms10authenticationservice.entity.UserCredential;
import com.karl.ms10authenticationservice.exception.UserRegistrationException;
import com.karl.ms10authenticationservice.repo.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static com.karl.ms10authenticationservice.constants.ExceptionMessages.*;
import static com.karl.ms10authenticationservice.utils.UserCredentialDtoConverter.toEntity;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PatternsConfig patternsConfig;

    public UserCredential saveUser(UserCredentialDTO credential)
            throws UserRegistrationException {

        if (Strings.isNullOrEmpty(credential.getUsername()))
            throw new UserRegistrationException(USERNAME_FIELD_IS_MISSING);

        if (Strings.isNullOrEmpty(credential.getEmail()))
            throw new UserRegistrationException(EMAIL_FIELD_IS_MISSING);

        if (Strings.isNullOrEmpty(credential.getPassword()))
            throw new UserRegistrationException(PASSWORD_FIELD_IS_MISSING);

        if (credential.getPassword().length() < 8)
            throw new UserRegistrationException(PASSWORD_IS_WEAK);

        if (!Pattern.compile(patternsConfig.getMatcher()).matcher(credential.getPassword()).matches())
            throw new UserRegistrationException(PASSWORD_FORMAT_MISMATCH);

        if (userCredentialRepository.findByUsername(credential.getUsername()).isPresent())
            throw new UserRegistrationException(USERNAME_ALREADY_TAKEN);

        credential.setPassword(passwordEncoder.encode(credential.getPassword()));

        return userCredentialRepository.save(toEntity(credential));
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}