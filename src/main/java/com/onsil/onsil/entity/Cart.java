package com.onsil.onsil.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CART")
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq_gen")
    @SequenceGenerator(name = "cart_seq_gen", sequenceName = "CART_SEQ", allocationSize = 1)
    @Column(name = "CARTID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBERID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTID", nullable = false)
    private Product product;

    // ★ 장바구니 기능을 위해 QUANTITY 컬럼 필수 - DB에 추가 필요
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @CreatedDate
    @Column(name = "ORDERDATE", updatable = false)
    private LocalDateTime orderDate;

    // ★ 수량 변경을 위한 메서드
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
