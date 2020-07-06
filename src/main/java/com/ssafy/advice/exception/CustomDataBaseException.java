package com.ssafy.advice.exception;

public class CustomDataBaseException extends RuntimeException{
    public CustomDataBaseException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomDataBaseException(String msg){
        super(msg);
    }
    public CustomDataBaseException(){
        super();
    }
}
