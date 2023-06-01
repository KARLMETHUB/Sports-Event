package com.karl.ms7ticketservice.constants;

public class ExceptionMessage {

    private ExceptionMessage() {}

    public static final String RESOURCE_NOT_FOUND = "Ticket with id: %d does not exist.";
    public static final String RESOURCE_MATCH_NOT_FOUND = "Match with id: %d does not exist.";
    public static final String ID_SHOULD_BE_NULL_ON_CREATE = "Key id should be null.";
    public static final String ID_REQUIRED = "Key id is required.";
    public static final String ID_PARAM_LESS_THAN_ZERO = "Id should not be less than zero(0).";
    public static final String SERVICE_UNAVAILABLE = "Match service is unavailable. Please try again later. ";

    public static final String CUSTOMER_NAME_REQUIRED = "Missing field: Customer Name field is required.";
    public static final String PRICE_REQUIRED = "Missing field: Price field is required.";
    public static final String PRICE_NEGATIVE = "Invalid value: Price field should not be negative.";
    public static final String MATCH_REQUIRED = "Missing field: Match field is required.";
    public static final String MATCH_ID_NON_EXISTENT = "Non Existent: Match id does not exist.";
    public static final String BAD_REQUEST_EXCEPTION = "Bad request exception occurred: {} ";
    public static final String FEIGN_EXCEPTION = "Bad request exception occurred: {} ";
    public static final String GENERIC_EXCEPTION = "Bad request exception occurred: {} ";

}
