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

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "REGDATE")
    private LocalDateTime regDate;

    @Column(name = "IMAGE")
    private String image;

    @PrePersist
    public void prePersist() {
        this.regDate = LocalDateTime.now();
    }
}
