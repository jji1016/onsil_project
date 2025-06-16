package com.onsil.onsil.product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewDto {
    private Integer id; // Review ID는 Integer
    private Integer productId; // Product ID는 Integer
    private Integer memberId; // Member ID를 Integer으로 받아서 처리
    private String userId; // Member의 userID (String)
    private String userName;
    private String nickname;
    private String content;
    private Integer rating;
    private LocalDateTime regDate;
    private String image;
}
