package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminSubscribeRepository extends JpaRepository<Subscribe, Integer> {

    List<OrderList> findByMember_id(int id);
}
