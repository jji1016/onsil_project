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
    private String unit;            // 품목단위
    private int amount;             // 재고수량
    private String warehouse;       // 보관창고
}
