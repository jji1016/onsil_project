package com.onsil.onsil.product.repository;

import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // ★ 장바구니 추가 시 상품명으로 검색하기 위한 메서드
    Optional<Product> findByFlowerName(String flowerName);
}
