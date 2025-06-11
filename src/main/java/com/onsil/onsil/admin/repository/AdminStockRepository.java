package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminStockRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT " +
            "p.PRODUCTID, " +
            "p.FLOWERNAME, " +
            "'개' AS UNIT, " +
            "COALESCE(s.QUANTITY, 0) AS AMOUNT, " +
            "'보관창고' AS WAREHOUSE " +
            "FROM PRODUCT p " +
            "LEFT JOIN STOCK s ON p.PRODUCTID = s.PRODUCTID " +
            "LEFT JOIN INPUT i ON p.PRODUCTID = i.PRODUCTID " +
            "WHERE (:flowerName IS NULL OR p.FLOWERNAME LIKE '%' || :flowerName || '%') " +
            "AND (:minPrice IS NULL OR p.PRICE >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.PRICE <= :maxPrice) " +
            "AND (:minStock IS NULL OR s.QUANTITY >= :minStock) " +
            "AND (:maxStock IS NULL OR s.QUANTITY <= :maxStock) " +
            // STOCK에 REGDATE가 없으므로, INPUT의 REGDATE로 날짜 필터링 (예시)
            "AND (:startDate IS NULL OR i.REGDATE >= :startDate) " +
            "AND (:endDate IS NULL OR i.REGDATE <= :endDate) " +
            "ORDER BY p.PRODUCTID",
            nativeQuery = true)
    List<Object[]> searchStockStatus(@Param("flowerName") String flowerName,
                                     @Param("minPrice") Integer minPrice,
                                     @Param("maxPrice") Integer maxPrice,
                                     @Param("minStock") Integer minStock,
                                     @Param("maxStock") Integer maxStock,
                                     @Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);
}
