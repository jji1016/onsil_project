package com.onsil.onsil.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    //쓰는지 안쓰는지 모름
//    private String userID;
//    private int quantity;
//    private String status;
//
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDateTime orderTime;
//
//    private String flowerName;
//    private int price;
//    private String image;
//    private String orderNum;
}
