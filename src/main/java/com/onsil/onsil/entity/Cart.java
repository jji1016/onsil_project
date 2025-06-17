package com.onsil.onsil.entity;

<<<<<<< HEAD
=======
import jakarta.persistence.*;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
<<<<<<< HEAD
@Table(name = "CART")
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
=======
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
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

<<<<<<< HEAD
    // ★ 장바구니 기능을 위해 QUANTITY 컬럼 필수 - DB에 추가 필요
=======
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @CreatedDate
    @Column(name = "ORDERDATE", updatable = false)
    private LocalDateTime orderDate;

<<<<<<< HEAD
    // ★ 수량 변경을 위한 메서드
=======
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
