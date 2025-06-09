package com.onsil.onsil.admin.repository;

import com.onsil.onsil.admin.dto.AdminInputDto;
import com.onsil.onsil.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//입고 목록 조회와 검색 기능
public interface AdminInputRepository extends JpaRepository<Input, Integer> {
    @Query(value = "SELECT i.AMOUNT, i.REGDATE, i.COMPANY, p.FLOWERNAME, m.USERNAME " +
            "FROM INPUT i " +
            "JOIN PRODUCT p ON i.PRODUCTID = p.PRODUCTID " +
            "JOIN MEMBER m ON i.PRODUCTID = m.MEMBERID " +
            "WHERE (:flowerName IS NULL OR p.FLOWERNAME LIKE %:flowerName%) " +
            "AND (:startDate IS NULL OR i.REGDATE >= :startDate) " +
            "AND (:endDate IS NULL OR i.REGDATE <= :endDate)", nativeQuery = true)
    List<Object[]> searchInputs(@Param("flowerName") String flowerName,
                                @Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate);


}

