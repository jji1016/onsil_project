package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminOrderListRepository extends JpaRepository<OrderList, Integer> {
    List<OrderList> findByMemberUserId(String userId);
}
