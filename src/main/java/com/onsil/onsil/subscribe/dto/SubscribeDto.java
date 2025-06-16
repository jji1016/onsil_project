package com.onsil.onsil.subscribe.dto;

import com.onsil.onsil.constant.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscribeDto {
    private Integer id;
    private Integer memberId;
    private Integer productId;
    private Period period;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String productName;
    private String productImage;
    private int price;
}
