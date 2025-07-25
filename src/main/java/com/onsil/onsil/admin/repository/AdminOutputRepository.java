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
    @Query(value =
            "SELECT COUNT(*) " +
                    "FROM OUTPUT o " +
                    "LEFT JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
                    "WHERE (:keyword IS NULL OR " +
                    "       (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
                    "       (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%')" +
                    "      ) " +
                    "AND (:startDate IS NULL OR o.REGDATE >= :startDate) " +
                    "AND (:endDate IS NULL OR o.REGDATE <= :endDate)",
            nativeQuery = true)
    int countOutputs(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(value =
            "SELECT * FROM (" +
                    "  SELECT inner_query.*, ROWNUM rnum FROM (" +
                    "    SELECT o.REGDATE, o.OUTPUTID, o.PRODUCTID, p.FLOWERNAME, o.AMOUNT " +
                    "    FROM OUTPUT o " +
                    "    JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
                    "    WHERE (:keyword IS NULL OR " +
                    "           (:category = 'flowerName' AND p.FLOWERNAME LIKE '%' || :keyword || '%') OR " +
                    "           (:category = 'productId' AND p.PRODUCTID LIKE '%' || :keyword || '%')" +
                    "          ) " +
                    "    AND (:startDate IS NULL OR o.REGDATE >= :startDate) " +
                    "    AND (:endDate IS NULL OR o.REGDATE <= :endDate) " +
                    "    ORDER BY o.REGDATE DESC" +
                    "  ) inner_query " +
                    "  WHERE ROWNUM <= :endRow" +
                    ") " +
                    "WHERE rnum > :startRow",
            nativeQuery = true)
    List<Object[]> searchOutputs(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );
}

