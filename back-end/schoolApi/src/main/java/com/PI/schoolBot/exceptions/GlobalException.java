package com.PI.schoolBot.exceptions;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 135469876516987L;

    private final Issue issue;
    private final HttpStatus httpStatus;

    public GlobalException(final Issue issue, HttpStatus httpStatus){
        super(issue.getMessage());
        this.issue = issue;
        this.httpStatus = httpStatus;
    }

    public Issue getIssue() {
        return issue;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
