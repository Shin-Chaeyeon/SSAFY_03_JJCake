package com.ssafy.vo;

import com.ssafy.util.Algorithm;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Message implements Serializable {
    private long idmessage;
    private long sender;
    private long receiver;
    private String title;
    private String contents;
    private String sendDate;
    private int isRead;
    private long pageIndex;
    private String marketName;
    private String nickname;
    private int flag;

    public void setSendDate(Timestamp timestamp) {
        this.sendDate = new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
    }
}