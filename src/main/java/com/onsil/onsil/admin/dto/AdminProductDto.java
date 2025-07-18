package com.onsil.onsil.admin.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminProductDto {
    private int productId;           // 품목코드 (PRODUCT.PRODUCTID)
    private String flowerName;       // 상품명 (PRODUCT.FLOWERNAME)
    private int price;               // 공급가 (PRODUCT.PRICE)
    private String sellPrice;
    private String category;
}

