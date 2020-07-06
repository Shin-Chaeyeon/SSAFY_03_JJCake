package com.ssafy.advice.exception;

public class CustomReviewNotFoundException extends RuntimeException{
    public CustomReviewNotFoundException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomReviewNotFoundException(String msg){
        super(msg);
    }
    public CustomReviewNotFoundException(){
        super();
    }
}
