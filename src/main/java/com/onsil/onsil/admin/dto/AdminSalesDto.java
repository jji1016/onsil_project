package com.onsil.onsil.admin.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSalesDto {
    private String label;        //x축: 날짜 / 꽃이름
    private String amount;       //y축: 매출금액
}

