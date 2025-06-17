package com.onsil.onsil.product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewDto {
<<<<<<< HEAD
    private Integer id; // Review ID는 Integer
    private Integer productId; // Product ID는 Integer
    private Integer memberId; // Member ID를 Integer으로 받아서 처리
    private String userId; // Member의 userID (String)
    private String userName;
    private String nickname;
    private String content;
    private Integer rating;
=======
    private Integer id;
    private Integer productId; //product 테이블 productID값
    private Integer memberId; //member테이블 memberiD값
    private String userId; //member테이블 useriD값
    private String memberName; //member테이블 userName값
    private String memberNickName; //member테이블 NickName값
    private String content;
    private int rating;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    private LocalDateTime regDate;
    private String image;
}
