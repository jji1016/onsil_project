package com.onsil.onsil.subscribe.repository;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    // member 객체로 구독 리스트 조회
    List<Subscribe> findByMember(Member member);

    // 상품 랜덤 6개 조회
    @Query("SELECT s FROM Subscribe s JOIN FETCH s.product p ORDER BY FUNCTION('DBMS_RANDOM.VALUE')")
    List<Subscribe> findRandom6Subscribes(org.springframework.data.domain.Pageable pageable);

    // 구독 ID로 해당하는 Product정보도 조회
    @Query("SELECT s FROM Subscribe s JOIN FETCH s.product WHERE s.id = :id")
    Optional<Subscribe> findWithProductById(@Param("id") Integer id);



}
