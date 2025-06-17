package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.*;
<<<<<<< HEAD
=======
import org.hibernate.annotations.Check;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69

import java.time.LocalDateTime;

@Entity
<<<<<<< HEAD
@Table(name = "REVIEW")
=======
@Setter
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
@Getter
@Setter
@NoArgsConstructor
<<<<<<< HEAD
@AllArgsConstructor
@Builder
=======
@EntityListeners(AuditingEntityListener.class)
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
public class Review {

    @Id
<<<<<<< HEAD
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEWID")
    private Integer reviewId;
=======
    @SequenceGenerator(name = "review_seq_gen", sequenceName = "REVIEW_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "reviewID")
    private Integer id;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
<<<<<<< HEAD
    @JoinColumn(name = "MEMBERID")
=======
    @JoinColumn(name = "subscribeID", nullable = false)
    private Subscribe subscribe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberID", nullable = false)
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
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
