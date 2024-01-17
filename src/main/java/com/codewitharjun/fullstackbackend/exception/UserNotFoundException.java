package com.codewitharjun.fullstackbackend.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id){
        super("Could not found the user with id "+id);
        System.out.println("reaching the user not found exception class...");
    }
}
