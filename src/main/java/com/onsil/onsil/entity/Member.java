package com.onsil.onsil.entity;

<<<<<<< HEAD
=======
import com.onsil.onsil.constant.Role;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
import com.onsil.onsil.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @Column(name = "memberID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String userID;

    @Column(nullable = false)
    private String userPW;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String address01;

    private String address02;

    @Column(nullable = false)
    private String zipcode;
<<<<<<< HEAD
=======

    @Column(nullable = false)
    private boolean deleteStatus;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69

    @CreatedDate
    private LocalDateTime regdate;

    @Builder.Default
<<<<<<< HEAD
    private String role = "ROLE_USER";
=======
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Subscribe> subscribeList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<OrderList> orderLists;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Output> outputList;

<<<<<<< HEAD
    private boolean deleteStatus = false; //탈퇴 할 경우 true로 변경
=======
    public void updateInfo(String nickName, String userEmail, String zipcode, String address01, String address02) {
        this.nickName = nickName;
        this.userEmail = userEmail;
        this.zipcode = zipcode;
        this.address01 = address01;
        this.address02 = address02;
    }

    public void updateInfo(String userPW, String userEmail, String tel, String zipcode, String address01, String address02) {
        this.userPW = userPW;
        this.userEmail = userEmail;
        this.tel = tel;
        this.zipcode = zipcode;
        this.address01 = address01;
        this.address02 = address02;
    }

    public void markAsDeleted() {
        this.deleteStatus = true;
    }
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69

    public MemberDto toMemberDto() {
        return MemberDto.builder()
                .id(this.getId())
                .userID(this.getUserID())
                .userName(this.getUserName())
                .userEmail(this.getUserEmail())
                .nickName(this.getNickName())
                .tel(this.getTel())
                .address01(this.getAddress01())
                .address02(this.getAddress02())
                .zipcode(this.getZipcode())
                .regDate(this.getRegdate())
                .modifyDate(this.getRegdate())
<<<<<<< HEAD
                .role(this.getRole())
=======
                .role(this.role)
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
                .build();
    }
}
