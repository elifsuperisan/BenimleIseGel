package com.project.benimleisegel.exception;

public class ResourceNotBelongsToUserException extends RuntimeException{
    public ResourceNotBelongsToUserException(String message){
        super(message);
    }
}
