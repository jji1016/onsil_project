package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminOrderListDao;
import com.onsil.onsil.admin.dto.AdminOrderDto;
import com.onsil.onsil.admin.dto.AdminOrderListDto;
import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.repository.AdminOrderListRepository;
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

    //private final AdminOrderListDao adminOrderListDao;
    private final AdminOrderListRepository adminOrderListRepository;

    public List<AdminOrderDto> searchOrderLists(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate, int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;
        List<Object[]> results = adminOrderListRepository.searchOrderLists(category, keyword, startDate, endDate, startRow, endRow);
        return results.stream().map(obj -> AdminOrderDto.builder()
                .orderTime(((Timestamp)obj[0]).toLocalDateTime())
                .orderListId(((Number)obj[1]).intValue())
                .flowerName((String)obj[2])
                .quantity(((Number)obj[3]).intValue())
                .status((String)obj[4])
                .userName((String)obj[5])
                .totalPrice(((Number)obj[6]).intValue())
                .build()).collect(Collectors.toList());
    }

    public int countOrderLists(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        return adminOrderListRepository.countOrderLists(category, keyword, startDate, endDate);
    }
}
