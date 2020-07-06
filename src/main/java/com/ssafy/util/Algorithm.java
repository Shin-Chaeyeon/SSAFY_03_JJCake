package com.ssafy.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public final class Algorithm {
    public static String timestampToString(Timestamp timestamp){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String result = null;
        long l = (now.getTime() - timestamp.getTime());
        long hour = (l / 1000) / 3600;
        long minute = (l / 1000) / 60 % 60;
        long second = (l / 1000) % 60;
        if (hour < 24) {
            if (hour == 0) {
                if (minute < 60) {
                    if (minute == 0)
                        result = second + "초전";
                    else
                        result = minute + "분전";
                }
            } else {
                result = hour + "시간전";
            }
        } else result = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(timestamp);
        return result;
    }
}
