package com.karl.teams.constants;

public class ResponseMessage {

    public static final String DELETE_MESSAGE_SUCCESS = "Team with id: %d has been deleted.";
    public static final String DELETE_MESSAGE_FAILED = "Team with id: %d has NOT been deleted.";


    public static final String PLAYER_ASSIGN_SUCCESS = "Player was successfully assigned to the team.";
    public static final String PLAYER_ASSIGN_FAILED = "Player was NOT removed from the team.";
    public static final String PLAYER_REMOVAL_SUCCESS = "Player was successfully removed from the team.";
    public static final String PLAYER_REMOVAL_FAILED = "Player was NOT removed from the team.";

    private ResponseMessage() {}

}
