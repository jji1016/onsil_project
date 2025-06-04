package com.onsil.onsil.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class AdminOrderListDto {
    private Integer id;
    private Integer memberid;
    private Integer productid;
    private LocalDateTime ordertime;
    private Integer quantity;
    private String status;
}
