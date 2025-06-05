package com.onsil.onsil.admin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminOutputDto {

    private LocalDate outDate;
    private String productCode;
    private String productName;
    private String productType;
    private String productUnit;
    private int quantity;
    private String storage;
    private String manager;
}
