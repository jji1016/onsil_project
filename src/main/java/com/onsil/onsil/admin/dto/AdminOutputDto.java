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
    private LocalDate regDate;      // 출고일자 (OUTPUT.REGDATE)
    private int productId;          // 품목코드 (가공된 PRODUCTID)
    private String flowerName;      // 품목명 (PRODUCT.FLOWERNAME)
    private int outputAmount;       // 출고수량 (OUTPUT.AMOUNT)
    private String userName;        // 담당자 (MEMBER.USERNAME)
}
