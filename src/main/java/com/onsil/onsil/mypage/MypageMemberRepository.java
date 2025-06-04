package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface MypageMemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByUserID(String userID);

    //주문 내역에 필요한 정보만 가져오는 쿼리(Object타입으로 받기)
    @Modifying
    @Query(value = "SELECT o.quantity, o.status, o.orderTime, p.flowerName, p.price " +
            "FROM ORDERLIST o " +
            "JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE m.MEMBERID = :loggedMemberID " +
            "ORDER BY o.ORDERTIME DESC ",nativeQuery = true)
    List<Object[]> findOrderList(@Param("loggedMemberID") Integer loggedMemberID);
}
