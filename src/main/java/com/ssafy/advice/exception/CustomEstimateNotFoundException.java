package com.ssafy.advice.exception;

public class CustomEstimateNotFoundException extends RuntimeException {
    public CustomEstimateNotFoundException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomEstimateNotFoundException(String msg){
        super(msg);
    }
    public CustomEstimateNotFoundException(){
        super();
    }
}
