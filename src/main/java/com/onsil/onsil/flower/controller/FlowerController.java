package com.onsil.onsil.flower.controller;

import com.onsil.onsil.flower.dto.FlowerDto;
import com.onsil.onsil.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flowers")
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

    // 이 달의 탄생화
    @GetMapping("/month/{month}/birth")
    public ResponseEntity<?> getBirthFlowers(@PathVariable int month) {
        try {
            return ResponseEntity.ok(flowerService.getBirthFlowersByMonth(month));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // 이 달의 꽃 추천(탄생화 제외)
    @GetMapping("/month/{month}/recommend")
    public ResponseEntity<?> getRecommendedFlowers(@PathVariable int month) {
        try {
            return ResponseEntity.ok(flowerService.getRecommendedFlowersByMonth(month));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // 월별 전체 꽃 리스트(간략 설명용)
    @GetMapping("/month/{month}/list")
    public ResponseEntity<?> getFlowerList(@PathVariable int month) {
        try {
            return ResponseEntity.ok(flowerService.getFlowersByMonth(month));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // 꽃 상세정보
    @GetMapping("/{dataNo}")
    public ResponseEntity<?> getFlowerDetail(@PathVariable Integer dataNo) {
        try {
            FlowerDto dto = flowerService.getFlowerDetailFromApi(dataNo);
            if (dto == null) {
                return ResponseEntity.status(404)
                        .body(Map.of("error", "꽃 정보를 찾을 수 없습니다."));
            }
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
