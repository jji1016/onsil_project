package com.onsil.onsil.mypage.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageSubscribeDto {
    private Integer subscribeID;
    private String period;
    private LocalDate startDate;
    private LocalDate endDate;
    private String flowerName;
    private String image;
    private int price;
}
