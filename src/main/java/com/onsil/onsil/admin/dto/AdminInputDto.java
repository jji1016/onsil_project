package com.onsil.onsil.admin.dto;

import com.onsil.onsil.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminInputDto {
    private LocalDateTime regDate;
    private Product product;
    private Integer productId;
    private int amount;
    private String company;
}
