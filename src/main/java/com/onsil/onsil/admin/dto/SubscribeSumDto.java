package com.onsil.onsil.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SubscribeSumDto {
    private List<SubscribeDto> list;
    private int totalPrice;
}
