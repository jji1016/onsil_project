package com.onsil.onsil.product.dto;

import com.onsil.onsil.entity.Review;
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
    private Integer productId; //product 테이블 productID값
    private Integer memberId; //member테이블 memberiD값
    private String userId; //member테이블 useriD값
    private String memberName; //member테이블 userName값
    private String memberNickName; //member테이블 NickName값
    private String content;
    private int rating;
    private LocalDateTime regDate;
    private String image;

    public static ReviewDto fromEntity(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setProductId(review.getProduct().getId());
        dto.setMemberId(review.getMember().getId());
        dto.setMemberNickName(review.getMember().getNickName());
        dto.setUserId(review.getMember().getUserID());
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        dto.setImage(review.getImage());
        dto.setRegDate(review.getRegDate());
        return dto;
    }
}
