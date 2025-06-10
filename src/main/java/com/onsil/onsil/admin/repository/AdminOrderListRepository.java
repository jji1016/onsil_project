package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminOrderListRepository extends JpaRepository<OrderList, Integer> {

    @Query(value = "SELECT o.ORDERTIME, " +                      // 0: 주문일시
            "m.MEMBERID, " +                                     // 1: memberId
            "p.FLOWERNAME, " +                                   // 2: 상품
            "o.QUANTITY, " +                                     // 3: 수량
            "p.PRICE, " +                                        // 4: 상품금액
            "o.STATUS, " +                                       // 5: 주문상태
            "m.USERID, " +                                       // 6: 주문자
            "m.USERNAME " +                                      // 7: 수령인
            //"o.PAYMENTMETHOD " +                               // 8: 결제수단
            "FROM ORDERLIST o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
            "WHERE (:userId IS NULL OR m.USERID LIKE '%' || :userId || '%') " +
            "AND (:status IS NULL OR o.STATUS = :status) " +
            "AND (:startDate IS NULL OR o.ORDERTIME >= :startDate) " +
            "AND (:endDate IS NULL OR o.ORDERTIME <= :endDate) " +
            "ORDER BY o.ORDERTIME DESC",
            nativeQuery = true)
    List<Object[]> searchOrderList(@Param("userId") String userId,
                                   @Param("status") String status,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);
}
