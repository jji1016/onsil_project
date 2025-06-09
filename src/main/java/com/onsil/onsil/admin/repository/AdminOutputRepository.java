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

    @Query(value = "SELECT o.AMOUNT, o.REGDATE, p.FLOWERNAME, m.USERNAME " +
            "FROM OUTPUT o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "JOIN MEMBER m ON o.PRODUCTID = m.PRODUCTID " +
            "WHERE (:flowerName IS NULL OR p.FLOWERNAME LIKE %:flwoerName%) " +
            "AND (:startDate IS NULL OR o.REGDATE >= :startDate) " +
            "AND (:endDate IS NULL OR o.REGDATE <= :endDate)",
            nativeQuery = true)
    List<Object[]> searchOutputs(@Param("flowerName") String flowerName,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    List<AdminOutputDto> searchByConditions(String keyword, LocalDate startDate, LocalDate endDate);

    List<Object[]> searchOutputs();
}

