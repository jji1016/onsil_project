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
public class AdminOrderListDto {
    private LocalDateTime orderTime;     //주문일시
    private int orderListId;             //주문번호
    private String flowerName;           //상품명
    private int quantity;                //수량
    private String status;               //주문상태
    private String userName;             //주문자
    private int totalPrice;              //총주문금액
}
