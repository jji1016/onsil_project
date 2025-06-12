package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminOrderListRepository extends JpaRepository<OrderList, Integer> {

    @Query(value = "SELECT o.ORDERTIME, " +                      // 주문일시
            "o.ORDERLISTID, " +                                  // 주문번호
            "p.FLOWERNAME, " +                                   // 상품
            "o.QUANTITY, " +                                     // 수량
            "o.STATUS, " +                                       // 주문상태
            "m.USERNAME, " +                                     // 총주문금액
            "(p.PRICE * o.QUANTITY) AS TOTALPRICE " +
            "FROM ORDERLIST o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
            "WHERE (:keyword IS NULL OR " +
            "       (:category = 'orderNo' AND o.ORDERLISTID LIKE '%' || :keyword || '%') OR " +
            "       (:category = 'productName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
            "       (:category = 'userName' AND m.USERNAME LIKE '%' || :keyword || '%')" +
            "      ) " +
            "AND (:startDate IS NULL OR o.ORDERTIME >= :startDate) " +
            "AND (:endDate IS NULL OR o.ORDERTIME <= :endDate) " +
            "ORDER BY o.ORDERTIME DESC",
            nativeQuery = true)
    List<Object[]> searchOrderLists(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
