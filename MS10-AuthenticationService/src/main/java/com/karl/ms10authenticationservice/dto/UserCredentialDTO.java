package com.karl.ms10authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class UserCredentialDTO {
    private int id;
    private String username;
    private String email;
    private String password;
}
