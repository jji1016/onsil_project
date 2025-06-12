package com.onsil.onsil.admin.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminStockDto {
    private int productId;          // 품목코드
    private String flowerName;      // 품목명
    private int price;              // 품목가격
    private int quantity;           // 재고수량
}
