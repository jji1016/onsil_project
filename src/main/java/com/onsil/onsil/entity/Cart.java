package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @CreatedDate
    @Column(name = "ORDERDATE", updatable = false)
    private LocalDateTime orderDate;

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
