package com.onsil.onsil.mypage;

import com.onsil.onsil.config.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageOrderListDto {
    private int quantity;
    private String status;
    private LocalDateTime orderTime;
    private String flowerName;
    private int price;
}
