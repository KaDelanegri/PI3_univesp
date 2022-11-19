package com.PI.schoolBot.exceptions;

import com.PI.schoolBot.enums.IssueEnum;
import org.springframework.http.HttpStatus;

public class SchoolAPIException extends GlobalException{
    public SchoolAPIException(final Issue issue, final HttpStatus httpStatus) {
        super(issue, httpStatus);
    }

    public static SchoolAPIException notFound(final String field){
        return new SchoolAPIException(new Issue(IssueEnum.NOT_FOUND, field), HttpStatus.NOT_FOUND);
    }
    public static SchoolAPIException conflict(){
        return new SchoolAPIException(new Issue(IssueEnum.CONFLICT), HttpStatus.CONFLICT);
    }
    public static SchoolAPIException forbidden(){
        return new SchoolAPIException(new Issue(IssueEnum.FORBIDDEN), HttpStatus.FORBIDDEN);
    }
    public static SchoolAPIException unauthorized(){
        return new SchoolAPIException(new Issue(IssueEnum.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }
}
