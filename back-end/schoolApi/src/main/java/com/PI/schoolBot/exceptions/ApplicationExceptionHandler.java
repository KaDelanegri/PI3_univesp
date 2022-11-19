package com.PI.schoolBot.exceptions;

import com.PI.schoolBot.enums.IssueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        if (HttpStatus.BAD_REQUEST.equals(status)) {
            return new ResponseEntity<>(
                    new Issue(IssueEnum.ERROR_ON_POST, ex.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Issue(IssueEnum.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<Issue> handleGlobalException(final GlobalException exception) {
        LOGGER.error(
                "Exception:{} - Message: {}",
                exception.getClass().getName(),
                exception.getIssue().getMessage());
        return new ResponseEntity<>(exception.getIssue(), exception.getHttpStatus());
    }
}
