package com.ssafy.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Review implements Serializable {
    private MultipartFile[] image;
    private long idreview;
    private long orderId;
    private long buyerId;
    private long sellerId;
    private long stars;
    private String contents;
    private String imageUrl;
    private long isDeleted;
    private long pageIndex;
}
