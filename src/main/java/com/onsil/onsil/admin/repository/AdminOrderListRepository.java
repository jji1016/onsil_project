package com.onsil.onsil.admin.repository;

import com.onsil.onsil.admin.dto.DeliveryStatusDto;
import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdminOrderListRepository extends JpaRepository<OrderList, Integer> {
    @Query(value =
            "SELECT COUNT(*) " +
                    "FROM ORDERLIST o " +
                    "LEFT JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
                    "LEFT JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
                    "WHERE (:keyword IS NULL OR " +
                    "      (:category = 'orderNo' AND o.ORDERLISTID LIKE '%' || :keyword || '%') OR " +
                    "       (:category = 'productName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
                    "       (:category = 'userName' AND m.USERNAME LIKE '%' || :keyword || '%')" +
                    "      ) " +
                    "AND (:startDate IS NULL OR o.ORDERTIME >= :startDate) " +
                    "AND (:endDate IS NULL OR o.ORDERTIME <= :endDate)",
            nativeQuery = true)
    int countOrderLists(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(value =
            "SELECT * FROM (" +
                    "  SELECT inner_query.*, ROWNUM rnum FROM (" +
                    "    SELECT o.ORDERTIME, o.ORDERLISTID, p.FLOWERNAME, o.QUANTITY, o.STATUS, m.USERNAME, " +
                    "           (p.PRICE * o.QUANTITY) AS TOTALPRICE " +
                    "    FROM ORDERLIST o " +
                    "    JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
                    "    JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
                    "    WHERE (:keyword IS NULL OR " +
                    "           (:category = 'orderNo' AND o.ORDERLISTID LIKE '%' || :keyword || '%') OR " +
                    "           (:category = 'productName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
                    "           (:category = 'userName' AND m.USERNAME LIKE '%' || :keyword || '%')" +
                    "          ) " +
                    "    AND (:startDate IS NULL OR o.ORDERTIME >= :startDate) " +
                    "    AND (:endDate IS NULL OR o.ORDERTIME <= :endDate) " +
                    "    ORDER BY o.ORDERTIME DESC" +
                    "  ) inner_query " +
                    "  WHERE ROWNUM <= :endRow" +
                    ") " +
                    "WHERE rnum > :startRow",
            nativeQuery = true)
    List<Object[]> searchOrderLists(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );

    @Query(value = """
    SELECT 
        SUM(CASE WHEN s.status = 'ORDERED' THEN 1 ELSE 0 END) AS ordered,
        SUM(CASE WHEN s.status = 'SHIPPED' THEN 1 ELSE 0 END) AS shipped,
        SUM(CASE WHEN s.status = 'DELIVERING' THEN 1 ELSE 0 END) AS delivering,
        SUM(CASE WHEN s.status = 'CANCELED' THEN 1 ELSE 0 END) AS canceled
    FROM orderList s
""", nativeQuery = true)
    DeliveryStatusDto countDeliveryStatuses();

    @Query("SELECT o FROM OrderList o JOIN FETCH o.member JOIN FETCH o.product")
    List<OrderList> findAllWithMemberAndProduct();

    @Query(value = "SELECT TO_CHAR(o.ORDERTIME, 'YYYY-MM') AS month, SUM(p.PRICE) " +
            "FROM ORDERLIST o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "GROUP BY TO_CHAR(o.ORDERTIME, 'YYYY-MM') " +
            "ORDER BY TO_CHAR(o.ORDERTIME, 'YYYY-MM')", nativeQuery = true)
    List<Object[]> getMonthlyOrderRevenue();
}
