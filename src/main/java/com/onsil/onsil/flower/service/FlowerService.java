package com.onsil.onsil.flower.service;

import com.onsil.onsil.flower.dto.FlowerDto;
import com.onsil.onsil.entity.Flower;
import com.onsil.onsil.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;

    // 월별 6개만 (DB에 이미 6개만 저장되어 있다고 가정)
    public List<FlowerDto> getFlowersByMonth(int month) {
        return flowerRepository.findByfMonth(month).stream()
                .map(FlowerDto::new)
                .collect(Collectors.toList());
    }

    public FlowerDto getFlowerDetail(Integer dataNo) {
        Optional<Flower> flower = flowerRepository.findById(dataNo);
        return flower.map(FlowerDto::new).orElse(null);
    }
}
