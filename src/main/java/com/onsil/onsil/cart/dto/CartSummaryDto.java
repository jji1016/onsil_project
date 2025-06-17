package com.onsil.onsil.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CartSummaryDto {
<<<<<<< HEAD
    private List<CartItemDto> items; // 장바구니 상품 목록
    private int totalPrice;          // 상품 총액
    private int deliveryFee;         // 배송비
    private int finalPrice;          // 최종 결제 금액
=======
    private List<CartItemDto> items;
    private int totalPrice;
    private int deliveryFee;
    private int finalPrice;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
}
