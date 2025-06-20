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

    private final AdminStockRepository adminStockRepository;

    public List<AdminStockDto> searchStocks(
            String category, String keyword,
            Integer minQuantity, Integer maxQuantity,
            Integer minPrice, Integer maxPrice,
            int page, int pageSize
    ) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;
        List<Object[]> results = adminStockRepository.searchStocks(
                category, keyword, minQuantity, maxQuantity, minPrice, maxPrice, startRow, endRow
        );
        return results.stream().map(obj -> AdminStockDto.builder()
                .productId(((Number)obj[0]).intValue())
                .flowerName((String)obj[1])
                .price(((Number)obj[2]).intValue())
                .quantity(((Number)obj[3]).intValue())
                .set("다발")
                .storage("장항동 온실하우스")
                .build()).collect(Collectors.toList());
    }

    public int countStocks(String category, String keyword, Integer minQuantity, Integer maxQuantity, Integer minPrice, Integer maxPrice) {
        return adminStockRepository.countStocks(category, keyword, minQuantity, maxQuantity, minPrice, maxPrice);
    }
}
