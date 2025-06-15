package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AdminSalesRepository extends JpaRepository<OrderList, Integer> {

    // 일별 매출 (Oracle 기준)
    @Query(value = "SELECT TO_CHAR(o.ORDERTIME, 'YYYY-MM-DD') AS label, SUM(p.PRICE * o.QUANTITY) AS amount " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE o.STATUS = '결제완료' AND o.ORDERTIME BETWEEN :startDate AND :endDate " +
            "GROUP BY TO_CHAR(o.ORDERTIME, 'YYYY-MM-DD') " +
            "ORDER BY TO_CHAR(o.ORDERTIME, 'YYYY-MM-DD')", nativeQuery = true)
    List<Object[]> findDailySales(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 주별 매출 (Oracle 기준)
    @Query(value = "SELECT TO_CHAR(o.ORDERTIME, 'YYYY') || '-' || TO_CHAR(o.ORDERTIME, 'IW') AS label, SUM(p.PRICE * o.QUANTITY) AS amount " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE o.STATUS = '결제완료' AND o.ORDERTIME BETWEEN :startDate AND :endDate " +
            "GROUP BY TO_CHAR(o.ORDERTIME, 'YYYY'), TO_CHAR(o.ORDERTIME, 'IW') " +
            "ORDER BY TO_CHAR(o.ORDERTIME, 'YYYY'), TO_CHAR(o.ORDERTIME, 'IW')", nativeQuery = true)
    List<Object[]> findWeeklySales(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 월별 매출 (Oracle 기준)
    @Query(value = "SELECT TO_CHAR(o.ORDERTIME, 'YYYY-MM') AS label, SUM(p.PRICE * o.QUANTITY) AS amount " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE o.STATUS = '결제완료' AND o.ORDERTIME BETWEEN :startDate AND :endDate " +
            "GROUP BY TO_CHAR(o.ORDERTIME, 'YYYY-MM') " +
            "ORDER BY TO_CHAR(o.ORDERTIME, 'YYYY-MM')", nativeQuery = true)
    List<Object[]> findMonthlySales(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 카테고리별(꽃이름별) 매출
    @Query(value = "SELECT p.FLOWERNAME AS label, SUM(p.PRICE * o.QUANTITY) AS amount " +
            "FROM ORDERLIST o JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE o.STATUS = '결제완료' AND o.ORDERTIME BETWEEN :startDate AND :endDate " +
            "GROUP BY p.FLOWERNAME " +
            "ORDER BY amount DESC", nativeQuery = true)
    List<Object[]> findCategorySales(@Param("startDate") String startDate, @Param("endDate") String endDate);
}


