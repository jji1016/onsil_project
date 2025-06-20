package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminStockRepository extends JpaRepository<Product, Integer> {

    @Query(value =
            "SELECT COUNT(*) " +
                    "FROM PRODUCT p " +
                    "LEFT JOIN STOCK s ON p.PRODUCTID = s.PRODUCTID " +
                    "WHERE (:keyword IS NULL OR " +
                    "       (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
                    "       (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%')" +
                    "      ) " +
                    "AND (:minQuantity IS NULL OR COALESCE(s.QUANTITY, 0) >= :minQuantity) " +
                    "AND (:maxQuantity IS NULL OR COALESCE(s.QUANTITY, 0) <= :maxQuantity) " +
                    "AND (:minPrice IS NULL OR p.PRICE >= :minPrice) " +
                    "AND (:maxPrice IS NULL OR p.PRICE <= :maxPrice)",
            nativeQuery = true)
    int countStocks(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("minQuantity") Integer minQuantity,
            @Param("maxQuantity") Integer maxQuantity,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice
    );


    @Query(value =
            "SELECT * FROM (" +
                    "  SELECT inner_query.*, ROWNUM rnum FROM (" +
                    "    SELECT p.PRODUCTID, p.FLOWERNAME, p.PRICE, COALESCE(s.QUANTITY, 0) as QUANTITY " +
                    "    FROM PRODUCT p " +
                    "    LEFT JOIN STOCK s ON p.PRODUCTID = s.PRODUCTID " +
                    "    WHERE (:keyword IS NULL OR " +
                    "           (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
                    "           (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%')" +
                    "          ) " +
                    "    AND (:minQuantity IS NULL OR COALESCE(s.QUANTITY, 0) >= :minQuantity) " +
                    "    AND (:maxQuantity IS NULL OR COALESCE(s.QUANTITY, 0) <= :maxQuantity) " +
                    "    AND (:minPrice IS NULL OR p.PRICE >= :minPrice) " +
                    "    AND (:maxPrice IS NULL OR p.PRICE <= :maxPrice) " +
                    "    ORDER BY p.PRODUCTID" +
                    "  ) inner_query " +
                    "  WHERE ROWNUM <= :endRow" +
                    ") " +
                    "WHERE rnum > :startRow",
            nativeQuery = true)
    List<Object[]> searchStocks(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("minQuantity") Integer minQuantity,
            @Param("maxQuantity") Integer maxQuantity,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );

}
