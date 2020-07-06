package com.ssafy.advice.exception;

public class CustomImageSaveException extends RuntimeException{
    public CustomImageSaveException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomImageSaveException(String msg){
        super(msg);
    }
    public CustomImageSaveException(){
        super();
    }
}
