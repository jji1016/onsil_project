package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminOrderListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminOrderListDao {

    private final AdminOrderListRepository adminOrderListRepository;

    public List<Object[]> searchOrderLists(String userId,
                                           String status,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate) {
        List<Object[]> list = adminOrderListRepository.searchOrderList(userId, status, startDate, endDate);
        log.info("DaoList={}",list.toString());
        return list;
    }
}
