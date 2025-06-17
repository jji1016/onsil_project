package com.onsil.onsil.entity;

import com.onsil.onsil.constant.Period;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class  Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "subscribeID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberID", nullable = false)
    private Member member;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Period period;

    @Column(name = "SUBSCRIBENAME")
    private String subscribeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        // period 값을 한 달로 강제 고정
        this.period = Period.MONTHLY;

        // startDate가 null인 경우 현재 시간으로 설정
        if (this.startDate == null) {
            this.startDate = LocalDateTime.now();
        }

        // endDate를 startDate 기준으로 1달 후로 설정
        this.endDate = this.startDate.plusMonths(1);
    }

}
