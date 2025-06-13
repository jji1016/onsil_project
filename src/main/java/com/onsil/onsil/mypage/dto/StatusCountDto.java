package com.onsil.onsil.mypage.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusCountDto {
    private int ORDERED;
    private int DELIVERING;
    private int SHIPPED;
    private int CANCELED;
}
