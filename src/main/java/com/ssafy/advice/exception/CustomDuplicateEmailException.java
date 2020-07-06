package com.ssafy.advice.exception;

public class CustomDuplicateEmailException extends RuntimeException{
    public CustomDuplicateEmailException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomDuplicateEmailException(String msg){
        super(msg);
    }
    public CustomDuplicateEmailException(){
        super();
    }
}
