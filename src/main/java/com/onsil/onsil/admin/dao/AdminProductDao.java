package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminProductDao {
    private final AdminProductRepository adminProductRepository;

    public List<Object[]> searchProducts(String category, String keyword, Integer minPrice, Integer maxPrice, int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;
        return adminProductRepository.searchProducts(category, keyword, minPrice, maxPrice, startRow, endRow);
    }
}
