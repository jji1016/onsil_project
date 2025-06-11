package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminStockDao;
import com.onsil.onsil.admin.dto.AdminStockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminStockService {

    private final AdminStockDao adminStockDao;

    public List<AdminStockDto> searchStocks(String flowerName,
                                            Integer minPrice,
                                            Integer maxPrice,
                                            Integer minStock,
                                            Integer maxStock,
                                            LocalDateTime startDate,
                                            LocalDateTime endDate) {

        List<Object[]> results = adminStockDao.searchStocks(flowerName, minPrice, maxPrice, minStock, maxStock, startDate, endDate);

        log.info("results: {}", results);
        return results.stream()
                .map(obj -> AdminStockDto.builder()
                        .productId(((Number)obj[0]).intValue())
                        .flowerName(String.valueOf(obj[1]))
                        .unit(String.valueOf(obj[2]))
                        .amount(((Number)obj[3]).intValue())
                        .build())
                .collect(Collectors.toList());
    }
}
