package com.onsil.onsil.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminInputDto {
    private LocalDateTime regDate;
    private int memberId;
    private String flowerName;
    private int amount;
    private String supplyName;
    private String userName;
}
