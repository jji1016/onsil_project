package com.onsil.onsil.entity;

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
@ToString
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

    @CreatedDate
    private LocalDateTime regdate;

    private String role = "ROLE_USER";

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

}
