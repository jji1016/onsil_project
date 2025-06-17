package com.onsil.onsil.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminOrderListDto {

    private String userID;
    private int quantity;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime orderTime;

    private String flowerName;
    private int price;
    private String image;

    private String orderNum;
}
