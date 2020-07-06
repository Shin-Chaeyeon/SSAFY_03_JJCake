package com.ssafy.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Market implements Serializable {
    private long idmarket;
    private long sellerId;
    private String name;
    private String intro;
    private String openTime;
    private String closeTime;
    private String dayOff;
    private String phoneNumber;
    private String address;
    private String deliveryArea;
    private String businessRegistrationNumber;
    private int isDeleted;
}
