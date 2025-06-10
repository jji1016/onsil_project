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
    private long subscribeCount;

}
