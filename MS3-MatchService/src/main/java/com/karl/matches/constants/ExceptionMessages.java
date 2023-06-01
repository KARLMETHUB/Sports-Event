package com.karl.matches.constants;

public class ExceptionMessages {

    private ExceptionMessages() {}
    public static final String ENTITY_MAPPING_EXCEPTION_MESSAGE = "Could not map to Entity";
    public static final String MATCH_EXISTS_EXCEPTION_MESSAGE = "Match ID already exists.";
    public static final String RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE = "Resource not found";

    public static final String RESOURCE_NOT_FOUND = "Match with id: %d does not exist.";
    public static final String ID_SHOULD_BE_NULL_ON_CREATE = "Key id should be null.";
    public static final String ID_REQUIRED = "Key id is required.";
    public static final String ID_PARAM_LESS_THAN_ZERO = "Id should not be less than zero(0).";

    public static final String MATCH_ID_REQUIRED = "Match id must not be null. ";
    public static final String EMPTY_RESPONSE_BODY = "Response body is null. ";

    public static final String FIELD_ID_REQUIRED = "Field Id is required.";
    public static final String FIELD_ID_NON_EXISTENT = "Field Id does not exist.";
    public static final String TOURNAMENT_ID_REQUIRED = "Tournament Id is required.";
    public static final String TOURNAMENT_ID_NON_EXISTENT = "Tournament Id does not exist.";
    public static final String HOME_TEAM_ID_REQUIRED = "Home Team Id is required.";
    public static final String HOME_TEAM_NON_EXISTENT = "Home Team Id does not exist.";
    public static final String AWAY_TEAM_ID_REQUIRED = "Away Team Id is required.";
    public static final String AWAY_TEAM_NON_EXISTENT = "Away Team Id does not exist.";

    public static final String SERVICE_UNAVAILABLE = "Service unavailable, Please try again later.";

    public static final String BAD_REQUEST_EXCEPTION = "Bad request exception occurred: {} ";
    public static final String FEIGN_EXCEPTION = "Bad request exception occurred: {} ";
    public static final String GENERIC_EXCEPTION = "Bad request exception occurred: {} ";

    public static final String RESOURCE_FIELD_NOT_FOUND = "Field with id: %d does not exist.";
    public static final String RESOURCE_TOURNAMENT_NOT_FOUND = "Tournament with id: %d does not exist.";
    public static final String RESOURCE_TEAM_NOT_FOUND = "Team with id: %d does not exist.";

}
