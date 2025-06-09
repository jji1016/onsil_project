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

        List<Object[]> objects = adminStockDao.searchStocks(flowerName, minPrice, maxPrice, minStock, maxStock, startDate, endDate);

        log.info("objects: {}", objects);
        List<AdminStockDto> adminStockDtos = objects.stream()
                .map(index -> new AdminStockDto(
                        ((String) index[0]),
                        ((Number) index[1]).intValue(),
                        ((String) index[2])
                ))
                .collect(Collectors.toList());
        return adminStockDtos;
    }
}
