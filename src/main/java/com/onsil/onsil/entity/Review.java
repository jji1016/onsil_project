package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEW")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEWID")
    private Integer reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBERID")
    private Member member;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "RATING", nullable = false)
    private Integer rating;

    @Column(name = "REGDATE", nullable = false, updatable = false)
    private LocalDateTime regDate;

    @Column(name = "IMAGE")
    private String image;

    // 리뷰 등록 시 자동으로 현재 시간이 regDate에 저장됩니다.
    @PrePersist
    public void prePersist() {
        this.regDate = LocalDateTime.now();
    }
}
