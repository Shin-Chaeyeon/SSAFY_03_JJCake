package com.ssafy.type;

public enum IsDeleted {
    FALSE(1), TRUE(2);
    public final int NUMBER;
    IsDeleted(int NUMBER){
        this.NUMBER = NUMBER;
    }
}
