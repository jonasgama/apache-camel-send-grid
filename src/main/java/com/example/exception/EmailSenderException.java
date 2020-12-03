package com.example.exception;

public class EmailSenderException extends Exception{
    public EmailSenderException(String message, Exception err){
        super(message, err);
    }
}
