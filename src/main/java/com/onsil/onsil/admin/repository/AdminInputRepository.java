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
    @Query(value =
            "SELECT COUNT(*) " +
                    "FROM INPUT i " +
                    "LEFT JOIN PRODUCT p ON i.PRODUCTID = p.PRODUCTID " +
                    "WHERE (:keyword IS NULL OR " +
                    "       (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
                    "       (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%')" +
                    "      ) " +
                    "AND (:startDate IS NULL OR i.REGDATE >= :startDate) " +
                    "AND (:endDate IS NULL OR i.REGDATE <= :endDate)",
            nativeQuery = true)
    int countInputs(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(value =
            "SELECT * FROM (" +
                    "  SELECT inner_query.*, ROWNUM rnum FROM (" +
                    "    SELECT i.REGDATE, i.INPUTID, i.PRODUCTID, p.FLOWERNAME, i.AMOUNT " +
                    "    FROM INPUT i " +
                    "    LEFT JOIN PRODUCT p ON i.PRODUCTID = p.PRODUCTID " +
                    "    WHERE (:keyword IS NULL OR " +
                    "           (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
                    "           (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%')" +
                    "          ) " +
                    "    AND (:startDate IS NULL OR i.REGDATE >= :startDate) " +
                    "    AND (:endDate IS NULL OR i.REGDATE <= :endDate) " +
                    "    ORDER BY i.REGDATE DESC" +
                    "  ) inner_query " +
                    "  WHERE ROWNUM <= :endRow" +
                    ") " +
                    "WHERE rnum > :startRow",
            nativeQuery = true)
    List<Object[]> searchInputs(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );
}

