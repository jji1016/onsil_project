package com.onsil.onsil.admin.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesSummaryDto {
    private BigDecimal todaySales;
    private BigDecimal monthSales;
    private Long monthOrderCount;
    private BigDecimal avgOrderValue;
}
