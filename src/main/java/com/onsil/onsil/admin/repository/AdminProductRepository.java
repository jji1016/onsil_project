package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminProductRepository extends JpaRepository<Product, Integer> {
    @Query(value =
            "SELECT COUNT(*) " +
                    "FROM PRODUCT p " +
//                    "LEFT JOIN PRODUCT p ON i.PRODUCTID = p.PRODUCTID " +
                    "WHERE (:keyword IS NULL OR " +
                    "       (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%') OR " +
                    "       (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%')" +
                    "      ) " +
                    "AND (:minPrice IS NULL OR p.PRICE >= :minPrice) " +
                    "AND (:maxPrice IS NULL OR p.PRICE <= :maxPrice)",
            nativeQuery = true)
    int countProducts(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice
    );

    @Query(value =
            "SELECT * FROM (" +
                    "  SELECT inner_query.*, ROWNUM rnum FROM (" +
                    "    SELECT p.PRODUCTID, p.FLOWERNAME, p.PRICE " +
                    "    FROM PRODUCT p " +
                    "    WHERE (:keyword IS NULL OR " +
                    "           (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%') OR " +
                    "           (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%')" +
                    "    ) " +
                    "    ORDER BY p.PRODUCTID" +
                    "  ) inner_query " +
                    "  WHERE ROWNUM <= :endRow" +
                    ") " +
                    "WHERE rnum > :startRow",
            nativeQuery = true)
    List<Object[]> searchProducts(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow

    );

    @Query(value = """
    SELECT p.PRODUCTID as productId,
           p.FLOWERNAME as flowerName,
           NVL(sub.subscribe_count, 0) + NVL(ord.order_count, 0) as totalCount
    FROM PRODUCT p
    LEFT JOIN (
        SELECT PRODUCTID, COUNT(*) as subscribe_count
        FROM SUBSCRIBE
        GROUP BY PRODUCTID
    ) sub ON p.PRODUCTID = sub.PRODUCTID
    LEFT JOIN (
        SELECT PRODUCTID, COUNT(*) as order_count
        FROM ORDERLIST
        GROUP BY PRODUCTID
    ) ord ON p.PRODUCTID = ord.PRODUCTID
    ORDER BY totalCount DESC
    FETCH FIRST 5 ROWS ONLY
    """, nativeQuery = true)
    List<Object[]> findPopularProducts();

}
