package com.onsil.onsil.subscribe.dao;

import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.subscribe.dto.SubscribeDto;
import com.onsil.onsil.subscribe.repository.SubscribeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SubscribeDao {

    private final SubscribeRepository subscribeRepository;

    public List<SubscribeDto> findRandom6Subscribes() {
        // 0번째 페이지, 6개 아이템 조회
        List<Subscribe> subscribeList = subscribeRepository.findRandom6Subscribes(PageRequest.of(0, 6));

        return subscribeList.stream().map(s -> SubscribeDto.builder()
                .id(s.getId())
                .memberId(s.getMember().getId())
                .productId(s.getProduct().getId())
                .productName(s.getProduct().getFlowerName())  // product 꽃이름
                .productImage(s.getProduct().getImage())     // product 이미지
                .price(s.getProduct().getPrice())            // product 가격
                .build()
        ).toList();
    }

    public SubscribeDto findById(Integer id) {
        Subscribe s = subscribeRepository.findWithProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("구독 없음"));
        return toDto(s);
    }

    private SubscribeDto toDto(Subscribe s) {
        return SubscribeDto.builder()
                .id(s.getId())
                .memberId(s.getMember().getId())
                .productId(s.getProduct().getId())
                .period(s.getPeriod())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .productName(s.getProduct().getFlowerName())
                .productImage(s.getProduct().getImage())
                .price(s.getProduct().getPrice())
                .build();
    }
}
