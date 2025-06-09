package com.onsil.onsil.admin.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminStockDto {
    private String flowerName;
    private int quantity;
    private String warehouse;

}
