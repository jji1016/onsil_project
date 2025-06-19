package com.onsil.onsil.mypage.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageOrderListDto {
    private Integer productId;
    private int quantity;
    private String status;
    private LocalDateTime orderTime;
    private String flowerName;
    private int price;
    private String image;
}
