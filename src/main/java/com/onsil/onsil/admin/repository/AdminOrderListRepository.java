package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminOrderListRepository extends JpaRepository<OrderList, Integer> {
}
