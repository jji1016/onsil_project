package com.onsil.onsil.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CartSummaryDto {
    private List<CartItemDto> items; // 장바구니 상품 목록
    private int totalPrice;          // 상품 총액
    private int deliveryFee;         // 배송비
    private int finalPrice;          // 최종 결제 금액
}
