package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p.PRODUCTID, p.FLOWERNAME, p.PRICE FROM PRODUCT p " +
            "WHERE (:keyword IS NULL OR " +
            "       (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%') OR " +
            "       (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') " +
            "      ) " +
            "ORDER BY p.PRODUCTID",
            nativeQuery = true)
    List<Object[]> searchProducts(
            @Param("category") String category,
            @Param("keyword") String keyword
    );

}
