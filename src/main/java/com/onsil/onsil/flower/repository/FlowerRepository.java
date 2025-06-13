package com.onsil.onsil.flower.repository;

import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FlowerRepository extends JpaRepository<Product, Integer> {
    // 월별 6개만 반환
    List<Product> findByFMonth(Integer fMonth, Pageable pageable);
}
