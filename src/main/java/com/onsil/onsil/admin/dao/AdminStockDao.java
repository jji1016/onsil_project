package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminStockRepository;
import com.onsil.onsil.admin.service.AdminStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminStockDao {

    private final AdminStockRepository adminStockRepository;

    public List<Object[]> searchStocks(String flowerName,
                                       Integer minPrice,
                                       Integer maxPrice,
                                       Integer minStock,
                                       Integer maxStock,
                                       LocalDateTime startDate,
                                       LocalDateTime endDate) {
        List<Object[]> list = adminStockRepository.searchStockStatus(flowerName, minPrice, maxPrice, minStock, maxStock, startDate, endDate);
        log.info("DaoList={}",list.toString());
        return list;
    }
}
