package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dto.ProductDto;

import java.util.List;

public interface AdminInventoryService {
    List<ProductDto> searchProducts(String keyword, String category, boolean isActive);
    void updateProduct(ProductDto productDto);
    void deleteProduct(ProductDto productDto);

    void deleteProduct(String productCode);
}
