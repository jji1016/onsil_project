package com.onsil.onsil.admin.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PopularCountDto {

    private Integer productId;
    private String flowerName;
<<<<<<< HEAD
    private Integer subscribeCount;

    // Long 타입의 count를 Integer로 변환하는 생성자 추가
    public PopularCountDto(Integer productId, String flowerName, Long subscribeCount) {
        this.productId = productId;
        this.flowerName = flowerName;
        this.subscribeCount = subscribeCount != null ? subscribeCount.intValue() : 0;
    }
=======
    private long subscribeCount;

>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
}
