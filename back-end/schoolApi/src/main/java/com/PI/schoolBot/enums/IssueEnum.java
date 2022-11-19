package com.PI.schoolBot.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.IllegalFormatException;

public enum IssueEnum {
    NOT_FOUND(1, "% not found"),
    CONFLICT(2, "This email is already registered"),
    FORBIDDEN(3, "You don't have access to this resource"),
    UNAUTHORIZED(4, "Email or password wrong"),
    ERROR_ON_POST(4, "We are facing some problems to receive your post %s"),
    INTERNAL_SERVER_ERROR(4, "Internal Server Error");

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueEnum.class);
    private final int code;
    private final String message;

    IssueEnum(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getFormattedMessage(final Object... args) {

        if (message == null) {
            return "";
        }

        try {
            return String.format(message, args);
        } catch (final IllegalFormatException e) {
            LOGGER.warn(e.getMessage(), e);
            return message.replace("%s", "");
        }
    }
}
