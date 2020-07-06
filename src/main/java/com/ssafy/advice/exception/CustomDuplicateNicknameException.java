package com.ssafy.advice.exception;

public class CustomDuplicateNicknameException extends RuntimeException{
    public CustomDuplicateNicknameException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomDuplicateNicknameException(String msg){
        super(msg);
    }
    public CustomDuplicateNicknameException(){
        super();
    }
}
