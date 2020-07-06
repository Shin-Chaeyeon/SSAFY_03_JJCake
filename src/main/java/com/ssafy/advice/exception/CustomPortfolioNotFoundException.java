package com.ssafy.advice.exception;

public class CustomPortfolioNotFoundException extends RuntimeException{
    public CustomPortfolioNotFoundException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomPortfolioNotFoundException(String msg){
        super(msg);
    }
    public CustomPortfolioNotFoundException(){
        super();
    }
}
