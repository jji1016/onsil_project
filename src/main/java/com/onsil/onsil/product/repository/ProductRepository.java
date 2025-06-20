package com.onsil.onsil.product.repository;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Subscribe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByFlowerName(String flowerName);

    @Query("SELECT p FROM Product p ORDER BY FUNCTION('DBMS_RANDOM.VALUE')")
    List<Product> findRandom6Subscribes(org.springframework.data.domain.Pageable pageable);

}
