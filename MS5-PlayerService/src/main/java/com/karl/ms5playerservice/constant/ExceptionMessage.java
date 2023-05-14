package com.karl.ms5playerservice.constant;

public class ExceptionMessage {

    public static final String RESOURCE_NOT_FOUND = "Player with id: %d does not exist.";

    /**
     * @deprecated (2023-05-11, Replaces with ID_SHOULD_BE_NULL_ON_CREATE, Just delete the variable)
     */
    @Deprecated(since="1.0", forRemoval=true)
    public static final String RESOURCE_EXISTS = "Player with id: %d already exist.";
    public static final String ID_SHOULD_BE_NULL_ON_CREATE = "Key id should be null.";
    public static final String ID_REQUIRED = "Key id is required.";
    public static final String ID_PARAM_LESS_THAN_ZERO = "Id should not be less than zero(0).";

    private ExceptionMessage() {}


}
