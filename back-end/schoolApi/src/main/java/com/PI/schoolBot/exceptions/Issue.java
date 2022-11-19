package com.PI.schoolBot.exceptions;

import com.PI.schoolBot.enums.IssueEnum;

import java.io.Serializable;

public class Issue implements Serializable {

    public static final long serialVersionUID = 879874968749687L;
    private final int code;
    private final String message;

    public Issue(final IssueEnum issueEnum) {
        this.code = issueEnum.getCode();
        this.message = issueEnum.getFormattedMessage();
    }

    public Issue(final IssueEnum issue, final Object... args) {
        code = issue.getCode();
        message = issue.getFormattedMessage(args);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        return String.format("Issue{code= '%s', message= '%s'}", code, message);
    }
}
