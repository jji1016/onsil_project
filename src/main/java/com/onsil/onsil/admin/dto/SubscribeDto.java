package com.onsil.onsil.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeDto {

    private int id;
    private String userID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String orderNumber;
    private String productName;
    private int productPrice;
    private String period;

}
