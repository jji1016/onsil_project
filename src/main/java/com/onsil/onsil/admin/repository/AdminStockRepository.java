package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminStockRepository extends JpaRepository<Stock, Integer> {

    @Query(value = "SELECT p.PRODUCTID, p.FLOWERNAME, p.PRICE, s.QUANTITY, p.IMAGE " +
            "FROM PRODUCT p " +
            "LEFT JOIN STOCK s ON p.PRODUCTID = s.PRODUCTIDB " +
            "WHERE (:flowerName IS NULL OR p.FLOWERNAME LIKE '%' || :flowerName || '%') " +
            "AND (:minPrice IS NULL OR p.PRICE >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.PRICE <= :maxPrice) " +
            "AND (:minStock IS NULL OR s.QUANTITY >= :minStock) " +
            "AND (:maxStock IS NULL OR s.QUANTITY <= :maxStock) " +
            "AND (:startDate IS NULL OR p.REGDATE >= :startDate) " +
            "AND (:endDate IS NULL OR p.REGDATE <= :endDate)",
            nativeQuery = true)
    List<Object[]> searchStockStatus(@Param("flowerName") String flowerName,
                                     @Param("minPrice") Integer minPrice,
                                     @Param("maxPrice") Integer maxPrice,
                                     @Param("minStock") Integer minStock,
                                     @Param("maxStock") Integer maxStock,
                                     @Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);
}
