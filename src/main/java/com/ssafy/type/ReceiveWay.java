package com.ssafy.type;

public enum ReceiveWay {
    PICK_UP(1), DELIVERY(2);
    public final int NUMBER;
    ReceiveWay(int NUMBER){
        this.NUMBER = NUMBER;
    }
}
