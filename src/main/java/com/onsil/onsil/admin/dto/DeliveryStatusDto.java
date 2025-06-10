package com.onsil.onsil.admin.dto;

import lombok.Getter;

public interface DeliveryStatusDto {
    int getOrdered();
    int getShipped();
    int getDelivering();
    int getCanceled();
}
