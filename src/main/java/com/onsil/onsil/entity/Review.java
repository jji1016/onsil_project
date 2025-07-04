package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @SequenceGenerator(name = "review_seq_gen", sequenceName = "REVIEW_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "reviewID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribeID", nullable = false)
    private Subscribe subscribe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberID", nullable = false)
    private Member member;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    @Check(constraints = "rating BETWEEN 1 AND 5")
    private int rating;

    @CreatedDate
    private LocalDateTime regDate;

    private String image;


}