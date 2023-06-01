package com.karl.ms10authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialDTONoPassword {

    private String userName;
    private String email;
    private String password;
}
