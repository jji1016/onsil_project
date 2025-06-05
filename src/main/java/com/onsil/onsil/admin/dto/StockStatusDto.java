package com.onsil.onsil.admin.dto;

import lombok.Data;

@Data
public class StockStatusDto {
    private String productCode;
    private String productName;
    private String category;
    private String unit;
    private int quantity;
    private String storageLocation;
}
