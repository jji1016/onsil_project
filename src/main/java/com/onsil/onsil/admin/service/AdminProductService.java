package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminProductDao;
import com.onsil.onsil.admin.dto.AdminProductDto;
import com.onsil.onsil.admin.repository.AdminProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    //private final AdminProductDao adminProductDao;
    private final AdminProductRepository adminProductRepository;

    public List<AdminProductDto> searchProducts(String category, String keyword, Integer minPrice, Integer maxPrice, int page, int pageSize ) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;
        List<Object[]> results = adminProductRepository.searchProducts(category, keyword, minPrice, maxPrice, startRow, endRow);
        return results.stream().map(obj -> AdminProductDto.builder()
                .productId(((Number)obj[0]).intValue())
                .flowerName((String)obj[1])
                .price(((Number)obj[2]).intValue())
                .sellPrice("25000")
                .category("절화")
                .build()).collect(Collectors.toList());
    }

    public int countProducts(String category, String keyword, Integer minPrice, Integer maxPrice) {
        return adminProductRepository.countProducts(category, keyword, minPrice, maxPrice);
    }
}
