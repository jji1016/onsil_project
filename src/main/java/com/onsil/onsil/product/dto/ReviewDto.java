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
    private Integer id;            // 리뷰 ID
    private Integer productId;     // 상품 ID
    private Integer memberId;      // 작성자 멤버 ID
    private String memberName;     // 작성자 이름 (Member.userName)
    private String content;        // 리뷰 내용
    private int rating;            // 평점 (1~5)
    private LocalDateTime regDate; // 작성일
    private String image;          // 리뷰 이미지 (있으면)
}
