package com.onsil.onsil.admin.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySalesDto {
    private LocalDate date;
    private BigDecimal sales;
}
