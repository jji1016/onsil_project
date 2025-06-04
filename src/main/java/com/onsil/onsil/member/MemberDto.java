package com.onsil.onsil.member;

import com.onsil.onsil.constant.Role;
import com.onsil.onsil.entity.Member;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    @NotBlank(message="아이디는 필수입력사항입니다.")
    private String userID;

    @NotBlank(message="패스워드는 필수입력사항입니다.")
    private String userPW;

    @NotBlank(message="이름은 필수입력사항입니다.")
    private String userName;

    @Email(message="이메일형식에 맞게 써주세요")
    @NotBlank(message="이메일은 필수입력사항입니다.")
    private String userEmail;

    @NotBlank(message="님네임은 필수입력사항입니다.")
    private String nickName;

    @NotBlank(message="전화번호는 필수입력사항입니다.")
    private String tel;

    @NotBlank(message="주소는 필수입력사항입니다.")
    private String address01;

    private String address02;

    @NotBlank(message="우편번호는 필수입력사항입니다.")
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private String role; //Role 상수 처리할 예정 enum

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

    public Member toMember() {
        return Member.builder()
                .userID(this.userID)
                .userPW(this.userPW)
                .userName(this.userName)
                .userEmail(this.userEmail)
                .nickName(this.nickName)
                .tel(this.tel)
                .address01(this.address01)
                .address02(this.address02)
                .zipcode(this.zipcode)
                .role(this.role)
                .build();
    }

}
