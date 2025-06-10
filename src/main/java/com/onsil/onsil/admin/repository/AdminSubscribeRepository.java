package com.onsil.onsil.admin.repository;

import com.onsil.onsil.admin.dto.PopularCountDto;
import com.onsil.onsil.entity.Subscribe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdminSubscribeRepository extends JpaRepository<Subscribe, Integer> {

    List<Subscribe> findByMember_id(int id);

    @Query("SELECT COUNT(DISTINCT s.member.id) FROM Subscribe s")
    int countDistinctNumber();

    @Query("SELECT COUNT(DISTINCT s.member.id) FROM Subscribe s WHERE s.startDate >= :todayDate")
    int countOneMonthMember(@Param("todayDate") LocalDateTime todayDate);

    @Query("SELECT new com.onsil.onsil.admin.dto.PopularCountDto(p.id, p.flowerName, COUNT(s)) " +
            "FROM Subscribe s JOIN s.product p " +
            "GROUP BY p.id, p.flowerName " +
            "ORDER BY COUNT(s) DESC")
    List<PopularCountDto> popularSubscribe(Pageable pageable);

    @Query("SELECT s FROM Subscribe s " +
            "WHERE s.startDate >= :oneMonthAgo " +
            "ORDER BY s.startDate DESC")
    List<Subscribe> findRecentInMonth(@Param("oneMonthAgo") LocalDateTime oneMonthAgo, Pageable pageable);

}
