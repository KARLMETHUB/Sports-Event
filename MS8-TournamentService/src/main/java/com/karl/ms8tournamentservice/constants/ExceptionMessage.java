package com.karl.ms8tournamentservice.constants;

public class ExceptionMessage {

    private ExceptionMessage() {}

    public static final String RESOURCE_NOT_FOUND = "Tournament with id: %d does not exist.";
    public static final String ID_SHOULD_BE_NULL_ON_CREATE = "Key id should be null.";
    public static final String ID_REQUIRED = "Key id is required.";
    public static final String ID_PARAM_LESS_THAN_ZERO = "Id should not be less than zero(0).";

}
