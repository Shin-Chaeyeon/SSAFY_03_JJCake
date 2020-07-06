package com.ssafy.advice.exception;

public class CustomImageDeleteException extends RuntimeException{
    public CustomImageDeleteException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomImageDeleteException(String msg){
        super(msg);
    }
    public CustomImageDeleteException(){
        super();
    }
}
