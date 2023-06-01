package com.karl.ms10authenticationservice.utils;

import com.karl.ms10authenticationservice.dto.UserCredentialDTO;
import com.karl.ms10authenticationservice.dto.UserCredentialDTONoPassword;
import com.karl.ms10authenticationservice.entity.UserCredential;

public class UserCredentialDtoConverter {
    private UserCredentialDtoConverter() {}

    public static UserCredential toEntity(UserCredentialDTO u) {
        UserCredential userCredential = new UserCredential();
        userCredential.setId(u.getId());
        userCredential.setUsername(u.getUsername());
        userCredential.setEmail(u.getEmail());
        userCredential.setPassword(u.getPassword());
        return userCredential;
    }

    public static UserCredentialDTO toDto(UserCredential u) {

        UserCredentialDTO userCredential = new UserCredentialDTO();
        userCredential.setId(u.getId());
        userCredential.setUsername(u.getUsername());
        userCredential.setEmail(u.getEmail());
        userCredential.setPassword(u.getPassword());
        return userCredential;
    }

    public static UserCredentialDTONoPassword toDtoNoPassword(UserCredential u) {

        UserCredentialDTONoPassword userCredential = new UserCredentialDTONoPassword();
        userCredential.setUserName(u.getUsername());
        userCredential.setEmail(u.getEmail());
        return userCredential;
    }


}
