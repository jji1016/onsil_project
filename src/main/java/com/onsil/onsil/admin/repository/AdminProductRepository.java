package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminProductRepository extends JpaRepository<Product, Integer> {

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
