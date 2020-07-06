package com.ssafy.advice.exception;

public class CustomMessageNotFoundException extends RuntimeException{
    public CustomMessageNotFoundException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomMessageNotFoundException(String msg){
        super(msg);
    }
    public CustomMessageNotFoundException(){
        super();
    }
}
