package com.ssafy.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Role {
    private long idrole;
    private long memberId;
    private String roleName;
}
