package com.onsil.onsil.mypage.repository;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.mypage.dto.StatusCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MypageMemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByUserID(String userID);

    //주문내역 리스트 총 개수
    @Query(value = "SELECT COUNT(*) FROM ORDERLIST o " +
            "JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE m.MEMBERID = :loggedMemberID " +
            "AND (:searchInfo IS NULL OR p.flowerName LIKE %:searchInfo%) " +
            "AND (:searchYear IS NULL OR TO_CHAR(o.ORDERTIME, 'YYYY') = :searchYear)",
            nativeQuery = true)
    int countSearchOrderList(@Param("loggedMemberID") Integer loggedMemberID,
                             @Param("searchInfo") String searchInfo,
                             @Param("searchYear") String searchYear);

    //주문내역 조회(상품 이름, 연도 기준 검색 가능)
    @Query(value = "SELECT * FROM ( " +
            "SELECT p.productID, o.quantity, o.status, o.orderTime, p.flowerName, p.price, p.image, ROW_NUMBER() OVER (ORDER BY o.ORDERTIME DESC) rn " +
            "FROM ORDERLIST o " +
            "JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "WHERE m.MEMBERID = :loggedMemberID " +
            "AND (:searchInfo IS NULL OR p.flowerName LIKE %:searchInfo%) " +
            "AND (:searchYear IS NULL OR TO_CHAR(o.ORDERTIME, 'YYYY') = :searchYear) " +
            ") WHERE rn BETWEEN :offset + 1 AND :offset + :itemsPerPage",
            nativeQuery = true)
    List<Object[]> findSearchOrderList(@Param("loggedMemberID") Integer loggedMemberID,
                                             @Param("searchInfo") String searchInfo,
                                             @Param("searchYear") String searchYear,
                                             @Param("offset") int offset,
                                             @Param("itemsPerPage") int itemsPerPage);

    //구독 리스트 전체 개수
    @Query(value = "SELECT COUNT(*) " +
            "FROM SUBSCRIBE s " +
            "JOIN PRODUCT p ON s.PRODUCTID = p.PRODUCTID " +
            "JOIN MEMBER m ON s.MEMBERID = m.MEMBERID " +
            "WHERE m.MEMBERID = :loggedMemberID ",
            nativeQuery = true)
    int countSubscribeList(Integer loggedMemberID);

    //구독 리스트 조회
    @Query(value = "SELECT * FROM ( " +
            "SELECT s.SUBSCRIBEID, s.PERIOD, s.STARTDATE, s.ENDDATE, p.FLOWERNAME, p.IMAGE, p.PRICE, ROW_NUMBER() OVER (ORDER BY s.ENDDATE) rn " +
            "FROM SUBSCRIBE s " +
            "JOIN PRODUCT p ON s.PRODUCTID = p.PRODUCTID " +
            "JOIN MEMBER m ON s.MEMBERID = m.MEMBERID " +
            "WHERE m.MEMBERID = :loggedMemberID " +
            ") WHERE rn BETWEEN :offset + 1 AND :offset + :itemsPerPage",
            nativeQuery = true)
    List<Object[]> findSubscribe(@Param("loggedMemberID") Integer loggedMemberID,
                                 @Param("offset") int offset,
                                 @Param("itemsPerPage") int itemsPerPage);

    //회원 탈퇴
    @Modifying
    @Query(value = "UPDATE MEMBER SET deleteStatus = 1 WHERE MEMBERID = :id",nativeQuery = true)
    int deleteAccount(@Param("id") Integer id);

    @Query(value =
            "SELECT " +
            "   SUM(DECODE(status, 'ORDERED', 1, 0)), " +
            "   SUM(DECODE(status, 'DELIVERING', 1, 0)), " +
            "   SUM(DECODE(status, 'SHIPPED', 1, 0)), " +
            "   SUM(DECODE(status, 'CANCELED', 1, 0)) " +
            "FROM ORDERLIST o " +
            "JOIN MEMBER m ON o.memberID = m.MEMBERID " +
            "WHERE m.MEMBERID = :loggedMemberID"
            ,nativeQuery = true)
    List<Object[]> statusCount(@Param("loggedMemberID") Integer loggedMemberID);
}
