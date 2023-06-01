package com.karl.ms10authenticationservice.constants;

public class ExceptionMessages {

    private ExceptionMessages() {}

    public static final String USERNAME_FIELD_IS_MISSING = "Field 'username' is missing.";
    public static final String EMAIL_FIELD_IS_MISSING = "Field 'email' is missing.";
    public static final String PASSWORD_FIELD_IS_MISSING = "Field 'password' is missing.";
    public static final String PASSWORD_IS_WEAK = "Weak password. Password length should be 8 or more.";
    public static final String PASSWORD_FORMAT_MISMATCH = "Password should be a combination of at least an uppercase alphabet, lowercase alphabets, a digit, and a special character.";
    public static final String USERNAME_ALREADY_TAKEN = "Username is already taken.";

    public static final String INVALID_TOKEN = "Token is invalid. You are not allowed to access this path.";
    public static final String TOKEN_VALID = "Token is valid.";
    public static final String TOKEN_INVALID = "Token is invalid.";
    public static final String TOKEN_EXPIRED = "Token is expired.";
    public static final String LOGIN_FAILED = "Login Failed: Your user ID or password is incorrect.";
}
