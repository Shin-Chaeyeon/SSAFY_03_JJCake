package com.ssafy.advice.exception;

public class CustomMarketNotFoundException extends RuntimeException{
    public CustomMarketNotFoundException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomMarketNotFoundException(String msg){
        super(msg);
    }
    public CustomMarketNotFoundException(){
        super();
    }
}
