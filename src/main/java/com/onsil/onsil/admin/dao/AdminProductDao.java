package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminProductDao {
    private final AdminProductRepository adminProductRepository;

    public List<Object[]> searchProducts(String category, String keyword) {
        return adminProductRepository.searchProducts(category, keyword);
    }
}
