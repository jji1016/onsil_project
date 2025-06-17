package com.onsil.onsil.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {
<<<<<<< HEAD
    private Integer cartId;        // 장바구니 ID (삭제/수정용)
    private Integer productId;     // 상품 ID
    private String flowerName;     // 상품명 (화면 표시용)
    private Integer price;         // 상품 가격
    private Integer quantity;      // 수량
    private String image;          // 상품 이미지
    private String memberNickName; // 회원 닉네임
=======
    private Integer cartId;
    private Integer productId;
    private String flowerName;
    private Integer price;
    private Integer quantity;
    private String image;
    private String memberNickName;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
}
