package com.onsil.onsil.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Integer id;
    private Integer productId;
    private Integer memberId;
    private String memberName;
    private String content;
    private int rating;
    private LocalDateTime regDate;
    private String image;
}
