package com.onsil.onsil.admin.dto;

import com.onsil.onsil.constant.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private String userID;
    private String userPW;
    private String userName;
    private String nickName;
    private String userEmail;
    private String zipcode;
    private String address01;
    private String address02;
    private String tel;
    private String role;
    private LocalDateTime regDate;

}
