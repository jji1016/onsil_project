package com.onsil.onsil.admin.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminOutputDto {
    private LocalDateTime regDate;   // 출고일자 (OUTPUT.REGDATE)
    private int outputId;            // 출고번호 (OUTPUT.OUTPUTID)
    private int productId;           // 품목코드 (PRODUCT.PRODUCTID)
    private String flowerName;       // 품목명 (PRODUCT.FLOWERNAME)
    private int amount;              // 출고수량 (OUTPUT.AMOUNT)
}
