package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SalesRepository extends JpaRepository<OrderList, Integer> {
    // 오늘 매출
    @Query(value = "SELECT COALESCE(SUM(p.PRICE * o.QUANTITY), 0) " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE TRUNC(o.ORDERTIME) = TRUNC(SYSDATE)", nativeQuery = true)
    BigDecimal getTodaySales();

    // 이번달 매출
    @Query(value = "SELECT COALESCE(SUM(p.PRICE * o.QUANTITY), 0) " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE TO_CHAR(o.ORDERTIME, 'YYYYMM') = TO_CHAR(SYSDATE, 'YYYYMM')", nativeQuery = true)
    BigDecimal getMonthSales();

    // 이번달 주문수
    @Query(value = "SELECT COUNT(*) FROM ORDERLIST o " +
            "WHERE TO_CHAR(o.ORDERTIME, 'YYYYMM') = TO_CHAR(SYSDATE, 'YYYYMM')", nativeQuery = true)
    Long getMonthOrderCount();

    // 이번달 평균 객단가
    @Query(value = "SELECT COALESCE(AVG(p.PRICE * o.QUANTITY), 0) " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE TO_CHAR(o.ORDERTIME, 'YYYYMM') = TO_CHAR(SYSDATE, 'YYYYMM')", nativeQuery = true)
    BigDecimal getMonthAvgOrderValue();

    // 일별 매출 (최근 10일)
    @Query(value = "SELECT TRUNC(o.ORDERTIME), COALESCE(SUM(p.PRICE * o.QUANTITY), 0) " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE o.ORDERTIME >= SYSDATE - 15 " +
            "GROUP BY TRUNC(o.ORDERTIME) ORDER BY TRUNC(o.ORDERTIME)", nativeQuery = true)
    List<Object[]> getDailySales();

    // 카테고리별 매출 (FLOWERINFO로 카테고리 분류 예시)
    @Query(value = "SELECT p.FLOWERINFO, COALESCE(SUM(p.PRICE * o.QUANTITY), 0) " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE TO_CHAR(o.ORDERTIME, 'YYYYMM') = TO_CHAR(SYSDATE, 'YYYYMM') " +
            "GROUP BY p.FLOWERINFO", nativeQuery = true)
    List<Object[]> getCategorySales();
}
