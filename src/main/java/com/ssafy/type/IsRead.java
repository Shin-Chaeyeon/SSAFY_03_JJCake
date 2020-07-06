package com.ssafy.type;

public enum IsRead {
    FALSE(1), TRUE(2);
    public final int NUMBER;
    IsRead(int NUMBER){
        this.NUMBER = NUMBER;
    }
}
