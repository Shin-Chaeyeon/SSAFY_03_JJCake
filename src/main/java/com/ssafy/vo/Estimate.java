package com.ssafy.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Estimate implements Serializable {
    private long idestimate;
    private long sellerId;
    private long orderId;
    private String contents;
    private int price;
    private int isSelected;
    private int isDeleted;
    private long pageIndex;
}
