package com.onsil.onsil.product.dao;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ProductDao {
    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = productList.stream().map(
            product -> ProductDto.builder()
                    .id(product.getId())
                    .flowerName(product.getFlowerName())
                    .flowerInfo(product.getFlowerInfo())
                    .price(product.getPrice())
                    .image(product.getImage())
                    .build()
        ).toList();
        return productDtoList;
    }
}
