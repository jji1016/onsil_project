package com.onsil.onsil.subscribe.repository;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    // member 객체로 구독 리스트 조회
    List<Subscribe> findByMember(Member member);

    // 상품 랜덤 6개 조회
    @Query("SELECT s FROM Subscribe s JOIN FETCH s.product p ORDER BY FUNCTION('DBMS_RANDOM.VALUE')")
    List<Subscribe> findRandom6Subscribes(org.springframework.data.domain.Pageable pageable);
}
