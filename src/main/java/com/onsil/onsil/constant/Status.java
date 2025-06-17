package com.onsil.onsil.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    ORDERED("배송 준비중"),
    DELIVERING("배송 중"),
    SHIPPED("배송 완료"),
    CANCELED("주문 취소");
    private final String label;

}
