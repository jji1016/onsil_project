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
public class AdminOrderListDto {
    private LocalDateTime orderDate;
    private int memberId;
    private String flowerName;
    private int quantity;
    private String price;
    private String orderStatus;
    private String orderName;
    private String receiveName;
}
