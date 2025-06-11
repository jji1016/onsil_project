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
    @Query(value = "SELECT i.REGDATE, i.INPUTID, p.PRODUCTID, p.FLOWERNAME, i.AMOUNT, i.COMPANY " +
            "FROM INPUT i " +
            "JOIN PRODUCT p ON i.PRODUCTID = p.PRODUCTID " +
            "WHERE " +
            "   (:category IS NULL OR " +
            "       (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
            "       (:category = 'productId' AND p.PRODUCTID = :keyword) OR " +
            "       (:category = 'company' AND i.COMPANY LIKE '%' || :keyword || '%') " +
            "   ) " +
            "AND (:startDate IS NULL OR i.REGDATE >= :startDate) " +
            "AND (:endDate IS NULL OR i.REGDATE <= :endDate) " +
            "ORDER BY i.REGDATE DESC",
            nativeQuery = true)
    List<Object[]> searchInputs(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

