package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminStockDao;
import com.onsil.onsil.admin.dto.AdminStockDto;
import com.onsil.onsil.admin.repository.AdminStockRepository;
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

    private final AdminStockRepository adminStockRepository;

    public List<AdminStockDto> searchStocks(
            String category, String keyword,
            Integer minQuantity, Integer maxQuantity,
            Integer minPrice, Integer maxPrice
    ) {
        List<Object[]> results = adminStockDao.searchStocks(
                category, keyword, minQuantity, maxQuantity, minPrice, maxPrice
        );
        return results.stream().map(obj -> AdminStockDto.builder()
                .productId(((Number)obj[0]).intValue())
                .flowerName((String)obj[1])
                .price(((Number)obj[2]).intValue())
                .quantity(((Number)obj[3]).intValue())
                .build()).collect(Collectors.toList());
    }

}
