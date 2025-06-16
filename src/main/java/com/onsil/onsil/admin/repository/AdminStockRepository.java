package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminStockRepository extends JpaRepository<Stock, Integer> {

    @Query(value = "SELECT " +
            "p.PRODUCTID, " +
            "p.FLOWERNAME, " +
            "p.PRICE, " +
            "COALESCE(s.QUANTITY, 0) " +
            "FROM PRODUCT p " +
            "LEFT JOIN STOCK s ON p.PRODUCTID = s.PRODUCTID " +
            "WHERE (:keyword IS NULL OR " +
            "       (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
            "       (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%')" +
            "      ) " +
            "AND (:minQuantity IS NULL OR COALESCE(s.QUANTITY, 0) >= :minQuantity) " +
            "AND (:maxQuantity IS NULL OR COALESCE(s.QUANTITY, 0) <= :maxQuantity) " +
            "AND (:minPrice IS NULL OR p.PRICE >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.PRICE <= :maxPrice) " +
            "ORDER BY p.PRODUCTID",
            nativeQuery = true)
    List<Object[]> searchStocks(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("minQuantity") Integer minQuantity,
            @Param("maxQuantity") Integer maxQuantity,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice
    );

}
