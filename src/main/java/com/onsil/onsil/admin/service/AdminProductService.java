package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminProductDao;
import com.onsil.onsil.admin.dto.AdminProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final AdminProductDao adminProductDao;

    public List<AdminProductDto> searchProducts(String category, String keyword) {
        List<Object[]> results = adminProductDao.searchProducts(category, keyword);
        return results.stream().map(obj -> AdminProductDto.builder()
                .productId(((Number)obj[0]).intValue())
                .flowerName((String)obj[1])
                .price(((Number)obj[2]).intValue())
                .build()).collect(Collectors.toList());
    }
}
