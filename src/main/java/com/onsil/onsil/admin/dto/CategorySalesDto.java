package com.onsil.onsil.admin.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySalesDto {
    private String category;
    private BigDecimal sales;
}
