package com.ssafy.type;

public enum Status {
    SUBMITTED(1), MATCHED(2), IN_PROGRESS(3), PRE_RECEIVED(4), RECEIVED(5);
    public final int NUMBER;
    Status(int NUMBER){
        this.NUMBER = NUMBER;
    }
}
