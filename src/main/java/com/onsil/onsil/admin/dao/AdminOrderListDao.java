package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminOrderListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminOrderListDao {

        private final AdminOrderListRepository adminOrderListRepository;

        public List<Object[]> searchOrderLists(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate, int page, int pageSize) {
            int startRow = (page - 1) * pageSize;
            int endRow = page * pageSize;
            return adminOrderListRepository.searchOrderLists(category, keyword, startDate, endDate, startRow, endRow);
        }
    }

