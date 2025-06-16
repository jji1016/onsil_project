package com.onsil.onsil.subscribe.dao;

import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.subscribe.dto.SubscribeDto;
import com.onsil.onsil.subscribe.repository.SubscribeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SubscribeDao {

    @PersistenceContext
    private EntityManager em;

    // 랜덤 6개 구독 리스트 조회 (상품 이름, 이미지 포함해서 DTO로 변환)
    public List<SubscribeDto> findRandom6Subscribes() {
        String jpql = "SELECT s FROM Subscribe s JOIN FETCH s.product p ORDER BY function('RAND')";
        TypedQuery<Subscribe> query = em.createQuery(jpql, Subscribe.class)
                .setMaxResults(6);
        List<Subscribe> subscribeList = query.getResultList();

        return subscribeList.stream().map(s -> SubscribeDto.builder()
                .id(s.getId())
                .memberId(s.getMember().getId())
                .productId(s.getProduct().getId())
                .period(s.getPeriod())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .productName(s.getProduct().getFlowerName())  // product 꽃이름
                .productImage(s.getProduct().getImage()) // product 이미지
                .price(s.getProduct().getPrice()) // product 가격
                .build()
        ).toList();
    }
}
