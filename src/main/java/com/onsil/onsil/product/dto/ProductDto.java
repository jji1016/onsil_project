package com.onsil.onsil.product.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDto {
    private Integer id; // Product.productId와 타입 일치
    private String flowerName;
    private String flowerInfo;
    private Integer price;
    private String image;
    private List<ReviewDto> reviews; // 제네릭 타입 명시
}
