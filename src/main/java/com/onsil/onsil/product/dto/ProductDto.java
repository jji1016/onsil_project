package com.onsil.onsil.product.dto;

import com.onsil.onsil.constant.Period;
import com.onsil.onsil.review.dto.ReviewDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Integer id; // Product.productId와 타입 일치
    private String flowerName;
    private String flowerInfo;
    private Integer price;
    private String image;
    private List<ReviewDto> reviews; // 제네릭 타입 명시
    private Integer memberId;
    private Integer productId;
    private String productName;
    private String productImage;
}
