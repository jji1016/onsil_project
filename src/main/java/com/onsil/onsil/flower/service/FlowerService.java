package com.onsil.onsil.flower.service;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.flower.dto.FlowerDto;
import com.onsil.onsil.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;

    // 월별 6개만 (3*2)
    public List<FlowerDto> getFlowersByMonth(int month) {
        return flowerRepository.findByfMonth(month, PageRequest.of(0, 6)).stream()
                .map(FlowerDto::new)
                .collect(Collectors.toList());
    }

    // 상세
    public FlowerDto getFlowerDetail(Integer id) {
        Optional<Product> flower = flowerRepository.findById(id);
        return flower.map(FlowerDto::new).orElse(null);
    }
}
