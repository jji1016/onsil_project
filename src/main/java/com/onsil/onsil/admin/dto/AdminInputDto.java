package com.onsil.onsil.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminInputDto {
    private LocalDateTime regDate;  // 입고일자 (INPUT.REGDATE)
    private int inputId;            // 입고번호
    private int productId;          // 품목코드 (PRODUCT.PRODUCTID)
    private String flowerName;      // 품목명 (PRODUCT.FLOWERNAME)
    private int amount;             // 입고수량 (INPUT.AMOUNT)
    private String company;         // 공급처 (INPUT.COMPANY)
}
