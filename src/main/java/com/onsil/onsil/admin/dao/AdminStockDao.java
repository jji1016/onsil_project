package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminStockRepository;
import com.onsil.onsil.admin.service.AdminStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminStockDao {
    private final AdminStockRepository adminStockRepository;

    public List<Object[]> searchStocks(
            String category, String keyword,
            Integer minQuantity, Integer maxQuantity,
            Integer minPrice, Integer maxPrice, int page, int pageSize
    ) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;
        return adminStockRepository.searchStocks(
                category, keyword, minQuantity, maxQuantity, minPrice, maxPrice, startRow, endRow
        );
    }
}

