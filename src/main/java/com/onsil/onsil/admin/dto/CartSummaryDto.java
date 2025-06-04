package com.onsil.onsil.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CartSummaryDto {
    private List<CartItemDto> items;
    private int totalPrice;
    private int deliveryFee;
    private int finalPrice;
}
