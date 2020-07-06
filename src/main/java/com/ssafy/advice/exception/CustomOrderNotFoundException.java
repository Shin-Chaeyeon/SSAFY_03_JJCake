package com.ssafy.advice.exception;

public class CustomOrderNotFoundException extends RuntimeException{
    public CustomOrderNotFoundException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomOrderNotFoundException(String msg){
        super(msg);
    }
    public CustomOrderNotFoundException(){
        super();
    }
}
