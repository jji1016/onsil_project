package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.entity.Subscribe;
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


}
