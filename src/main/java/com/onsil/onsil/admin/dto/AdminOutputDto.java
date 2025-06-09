package com.onsil.onsil.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminOutputDto {

    private int amount;
    private LocalDateTime regDate;
    private String flowerName;
    private String userName;
}
