package com.onsil.onsil.admin.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSalesDashboardDto {
    private List<AdminSalesDto> periodSales;    // 기간별 매출
    private List<AdminSalesDto> categorySales;  // 카테고리별 매출
}
