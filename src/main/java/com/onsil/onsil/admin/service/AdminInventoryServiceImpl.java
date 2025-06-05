package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dto.ProductDto;
import com.onsil.onsil.admin.repository.ProductRepository;
import com.onsil.onsil.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminInventoryServiceImpl implements AdminInventoryService{

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> searchProducts(String keyword, String category, boolean isActive) {
        List<Product> products = productRepository.findByProductNameContaining(keyword);
        return products.stream()
                .map(product -> ProductDto.builder()
                        .productCode(product.getProductCode())
                        .productName(product.getProductName())
                        .category(product.getCategory())
                        .unit(product.getUnit())
                        .stock(product.getStock())
                        .storage(product.getStorage())
                        .price(product.getPrice())
                        .isActive(product.isActive())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(dto.getProductCode()).orElseThrow();
        product.setStock(dto.getStock());
        product.setStorage(dto.getStorage());
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(String productCode) {
        productRepository.deleteById(productCode);
    }
}
