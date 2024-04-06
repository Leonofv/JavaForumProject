package com.example.ForumProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MessagePermissionException extends RuntimeException {

    public MessagePermissionException(String message) {
        super(message);
    }
}

