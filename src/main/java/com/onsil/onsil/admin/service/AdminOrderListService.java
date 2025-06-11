package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminOrderListDao;
import com.onsil.onsil.admin.dto.AdminOrderListDto;
import com.onsil.onsil.admin.dto.AdminOutputDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderListService {

    private final AdminOrderListDao adminOrderListDao;

    public List<AdminOrderListDto> searchOrderlists(String userId,
                                                    String status,
                                                    LocalDateTime startDate,
                                                    LocalDateTime endDate) {

        List<Object[]> objects = adminOrderListDao.searchOrderLists(userId, status, startDate, endDate);

        log.info("objects: {}", objects);

        List<AdminOrderListDto> adminOrderListDtos = objects.stream()
                .map(row -> new AdminOrderListDto(
                        ((Timestamp) row[0]).toLocalDateTime(),         // orderDate
                        ((Number) row[1]).intValue(),
                        String.valueOf(row[2]), // memberId
                        ((Number) row[3]).intValue(),                   // quantity
                        String.valueOf(row[4]),                         // price
                        String.valueOf(row[5]),                         // orderStatus
                        String.valueOf(row[6]),                         // orderName
                        String.valueOf(row[7])                          // receiveName
                        //String.valueOf(row[8])                        // paymentMethod
                ))
                .collect(Collectors.toList());
        return adminOrderListDtos;
    }
}
