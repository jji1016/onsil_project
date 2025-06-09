package com.onsil.onsil.entity;

import com.onsil.onsil.admin.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
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
@EntityListeners(AuditingEntityListener.class)
//@ToString
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

    @Column(nullable = false)
    private boolean deleteStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regdate;

    @Column(nullable = false)
    private String role = "ROLE_MEMBER";

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Review> reviewList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Subscribe> subscribeList;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<OrderList> orderLists;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Output> outputList;

    public void updateInfo(String nickName, String userEmail, String zipcode, String address01, String address02) {
        this.nickName = nickName;
        this.userEmail = userEmail;
        this.zipcode = zipcode;
        this.address01 = address01;
        this.address02 = address02;
    }

    public void markAsDeleted() {
        this.deleteStatus = true;
    }

}
