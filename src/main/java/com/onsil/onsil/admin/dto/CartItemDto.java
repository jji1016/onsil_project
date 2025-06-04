package com.onsil.onsil.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {
    private Integer cartId;
    private Integer productId;
    private String flowerName;
    private Integer price;
    private Integer quantity;
    private String image;
    private String memberNickName;
}
