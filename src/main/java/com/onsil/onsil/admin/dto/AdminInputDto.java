package com.onsil.onsil.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminInputDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDate;  // 입고일자 (INPUT.REGDATE)

    private int inputId;            // 입고번호 (INPUT.INPUTID)
    private int productId;          // 품목코드 (PRODUCT.PRODUCTID)
    private String flowerName;      // 품목명 (PRODUCT.FLOWERNAME)
    private int amount;             // 입고수량 (INPUT.AMOUNT)
    private String supplier;
    private String manager;

}
