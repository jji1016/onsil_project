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
    private Integer memberId; //member테이블 memberiD값
    private String userId; //member테이블 useriD값
    private String memberName;
    private String content;
    private int rating;
    private LocalDateTime regDate;
    private String image;
}
