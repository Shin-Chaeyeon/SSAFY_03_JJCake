package com.ssafy.vo;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Portfolio implements Serializable {
    private MultipartFile[] image;
    private long idportfolio;
    private long sellerId;
    private String title;
    private String contents;
    private String imageUrl;
    private int isDeleted;
    private long pageIndex;
}
