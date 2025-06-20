package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AdminSalesRepository extends JpaRepository<OrderList, Integer> {
    // 일별 매출
    @Query(value = "SELECT TO_CHAR(o.ORDERTIME, 'YYYY-MM-DD') AS label, SUM(p.PRICE * o.QUANTITY) AS amount " +
            "FROM ORDERLIST o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "GROUP BY TO_CHAR(o.ORDERTIME, 'YYYY-MM-DD') " +
            "ORDER BY TO_CHAR(o.ORDERTIME, 'YYYY-MM-DD')", nativeQuery = true)
    List<Object[]> findDailySales();

    // 월별 매출
    @Query(value = "SELECT TO_CHAR(o.ORDERTIME, 'YYYY-MM') AS label, SUM(p.PRICE * o.QUANTITY) AS amount " +
            "FROM ORDERLIST o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "GROUP BY TO_CHAR(o.ORDERTIME, 'YYYY-MM') " +
            "ORDER BY TO_CHAR(o.ORDERTIME, 'YYYY-MM')", nativeQuery = true)
    List<Object[]> findMonthlySales();

    // 년별 매출
    @Query(value = "SELECT TO_CHAR(o.ORDERTIME, 'YYYY') AS label, SUM(p.PRICE * o.QUANTITY) AS amount " +
            "FROM ORDERLIST o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "GROUP BY TO_CHAR(o.ORDERTIME, 'YYYY') " +
            "ORDER BY TO_CHAR(o.ORDERTIME, 'YYYY')", nativeQuery = true)
    List<Object[]> findYearlySales();

    // 카테고리별(꽃이름별) 매출
    @Query(value = "SELECT p.FLOWERNAME AS label, SUM(p.PRICE * o.QUANTITY) AS amount " +
            "FROM ORDERLIST o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "GROUP BY p.FLOWERNAME " +
            "ORDER BY amount DESC", nativeQuery = true)
    List<Object[]> findCategorySales();
}


