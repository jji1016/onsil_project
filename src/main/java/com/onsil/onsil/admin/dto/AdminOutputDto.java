package com.onsil.onsil.admin.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminOutputDto {
    private int amount;
    private String flowerName;
    private String userName;
    private LocalDateTime regDate;

}
