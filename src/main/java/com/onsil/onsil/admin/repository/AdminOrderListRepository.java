package com.onsil.onsil.admin.repository;

import com.onsil.onsil.admin.dto.DeliveryStatusDto;
import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminOrderListRepository extends JpaRepository<OrderList, Integer> {
    @Query(value = """
    SELECT 
        SUM(CASE WHEN s.status = 'ORDERED' THEN 1 ELSE 0 END) AS ordered,
        SUM(CASE WHEN s.status = 'SHIPPED' THEN 1 ELSE 0 END) AS shipped,
        SUM(CASE WHEN s.status = 'DELIVERED' THEN 1 ELSE 0 END) AS delivered,
        SUM(CASE WHEN s.status = 'CANCELED' THEN 1 ELSE 0 END) AS canceled
    FROM orderList s
""", nativeQuery = true)
    DeliveryStatusDto countDeliveryStatuses();
}
