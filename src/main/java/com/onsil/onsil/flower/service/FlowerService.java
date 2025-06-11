// src/main/java/com/onsil/onsil/flower/service/FlowerService.java
package com.onsil.onsil.flower.service;

import com.onsil.onsil.flower.dto.FlowerDto;
import com.onsil.onsil.entity.Flower;
import com.onsil.onsil.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;

    public List<FlowerDto> getFlowersByMonth(int month) {
        return flowerRepository.findByfMonth(month).stream()
                .map(FlowerDto::new)
                .collect(Collectors.toList());
    }

    public List<FlowerDto> getRecommendedFlowersByMonth(int month) {
        List<FlowerDto> all = getFlowersByMonth(month);
        int n = Math.min(5, all.size());
        Collections.shuffle(all);
        return all.subList(0, n);
    }

    public List<FlowerDto> getBirthFlowersByMonth(int month) {
        return getFlowersByMonth(month);
    }

    public FlowerDto getFlowerDetailFromApi(Integer dataNo) {
        Optional<Flower> flower = flowerRepository.findByDataNo(dataNo);
        return flower.map(FlowerDto::new).orElse(null);
    }
}
