package com.onsil.onsil.admin.repository;

import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AdminOutputRepository extends JpaRepository<Output, Integer> {

    @Query(value = "SELECT o.REGDATE, p.PRODUCTID, p.FLOWERNAME, o.AMOUNT, m.USERNAME " +
            "FROM OUTPUT o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "JOIN MEMBER m ON o.MEMBERID = m.MEMBERID " +
            "WHERE (:category IS NULL OR " +
            "       (:category = 'productName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
            "       (:category = 'productCode' AND p.PRODUCTID = :keyword) OR " +
            "       (:category = 'userName' AND m.USERNAME LIKE '%' || :keyword || '%') " +
            "      ) " +
            "AND (:startDate IS NULL OR o.REGDATE >= :startDate) " +
            "AND (:endDate IS NULL OR o.REGDATE <= :endDate) " +
            "ORDER BY o.REGDATE DESC",
            nativeQuery = true)
    List<Object[]> searchOutputs(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

