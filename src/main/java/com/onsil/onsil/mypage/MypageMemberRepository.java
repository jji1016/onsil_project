package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MypageMemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByUserID(String userID);

    //주문 내역에 필요한 정보만 가져오는 쿼리(Object타입으로 받기)
    @Query(value = "SELECT o.quantity, o.status, o.orderTime, p.flowerName, p.price, p.image " +
            "FROM ORDERLIST o " +
            "JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE m.MEMBERID = :loggedMemberID " +
            "ORDER BY o.ORDERTIME DESC ",nativeQuery = true)
    List<Object[]> findOrderList(@Param("loggedMemberID") Integer loggedMemberID);

    //주문내역 페이지 검색기반 조회
    @Query(value = "SELECT o.quantity, o.status, o.orderTime, p.flowerName, p.price, p.image " +
            "FROM ORDERLIST o " +
            "JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE m.MEMBERID = :loggedMemberID " +
            "AND ( :category IS NULL OR " +
            "   (:category = 'status' AND o.status LIKE %:keyword%) OR " +
            "   (:category = 'flowerName' AND p.flowerName LIKE %:keyword%) " +
            ") " +
            "AND ( :startDate IS NULL OR o.ORDERTIME >= :startDate ) " +
            "AND ( :endDate IS NULL OR o.ORDERTIME <= :endDate ) " +
            "ORDER BY o.ORDERTIME DESC",
            nativeQuery = true)
    List<Object[]> findSearchOrderList(
            @Param("loggedMemberID") Integer loggedMemberID,
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );




    @Modifying
    @Query(value = "UPDATE MEMBER SET deleteStatus = 1 WHERE MEMBERID = :id",nativeQuery = true)
    int deleteAccount(@Param("id") Integer id);
}
