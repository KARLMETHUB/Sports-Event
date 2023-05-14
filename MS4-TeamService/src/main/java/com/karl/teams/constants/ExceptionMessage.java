package com.karl.teams.constants;

public class ExceptionMessage {

    public static final String RESOURCE_NOT_FOUND = "Team with id: %d does not exist.";

    public static final String ID_SHOULD_BE_NULL_ON_CREATE = "Key id should be null.";
    public static final String ID_REQUIRED = "Key id is required.";
    public static final String ID_PARAM_LESS_THAN_ZERO = "Id should not be less than zero(0).";

    public static final String PLAYER_ID_REQUIRED = "Player id must not be null. ";
    public static final String TEAM_ID_REQUIRED = "Team id must not be null. ";
    public static final String PLAYER_ALREADY_ASSIGNED = "Player is already assigned to team. ";
    public static final String EMPTY_RESPONSE_BODY = "Response body is null. ";



    private ExceptionMessage() {}


}