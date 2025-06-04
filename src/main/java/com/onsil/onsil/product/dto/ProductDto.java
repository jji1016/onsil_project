package com.onsil.onsil.product.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String flowerName;
    private String flowerInfo;
    private int price;
    private String image;

    private List<ReviewDto> reviews;
}
