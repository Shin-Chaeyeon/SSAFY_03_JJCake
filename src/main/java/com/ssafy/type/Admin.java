package com.ssafy.type;

public enum Admin {
    BUYER(1), SELLER(2), ADMIN(3);
    public final int NUMBER;
    Admin(int NUMBER){
        this.NUMBER = NUMBER;
    }
}
