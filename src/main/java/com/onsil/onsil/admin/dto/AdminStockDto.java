package com.onsil.onsil.admin.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminStockDto {
    private int productId;
    private String flowerName;
    private int quantity;
}
