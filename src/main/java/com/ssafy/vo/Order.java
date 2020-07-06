package com.ssafy.vo;

import com.ssafy.util.Algorithm;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Order implements Serializable {
    private MultipartFile[] image;
    private long idorder;
    private long buyerId;
    private long sellerId;
    private String title;
    private String contents;
    private int status;
    private int maxPrice;
    private int radius;
    private int receiveWay;
    private String receiveDate;
    private String createDate;
    private String dueDate;
    private String imageUrl;
    private String cakeFlavor;
    private String cakeSize;
    private String cakeShape;
    private String cakeBackgroundColor;
    private String cakeCreamColor;
    private String cakeLettering;
    private String cakeBorderPosition;
    private int isDeleted;
    private long pageIndex;

    public void setCreateDate(Timestamp timestamp){
        this.createDate = Algorithm.timestampToString(timestamp);
    }

    public void setReceiveDate(Timestamp timestamp){
        this.receiveDate = new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
    }

    public void setDueDate(Timestamp timestamp){
        this.dueDate = new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
    }
}
