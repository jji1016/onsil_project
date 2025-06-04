package com.onsil.onsil.product.service;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.product.dao.ProductDao;
import com.onsil.onsil.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public ProductDto getProductById(int id) {
        return productDao.findByDtoId(id);
    }

    public List<ProductDto> findAll() {
        return productDao.findAll();
    }
}
