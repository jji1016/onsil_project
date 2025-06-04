package com.onsil.onsil.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Integer id;           // 멤버 ID
    private String userID;        // 로그인 아이디
    private String userName;      // 이름
    private String userEmail;     // 이메일
    private String nickName;      // 닉네임
    private String tel;           // 전화번호
    private String address01;     // 주소1
    private String address02;     // 주소2 (선택)
    private int zipcode;          // 우편번호
    private LocalDateTime regdate;// 가입일
    private String role;          // 권한 (ex. ROLE_MEMBER)

    private List<ReviewDto> reviews;
}
