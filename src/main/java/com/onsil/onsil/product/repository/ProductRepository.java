package com.onsil.onsil.product.repository;

import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByFlowerName(String flowerName);

    // 대소문자 구분 없이 찾고 싶으면 아래도 추가 가능
    Optional<Product> findByFlowerNameIgnoreCase(String flowerName);
}