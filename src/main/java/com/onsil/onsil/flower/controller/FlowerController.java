// src/main/java/com/onsil/onsil/flower/controller/FlowerController.java
package com.onsil.onsil.flower.controller;

import com.onsil.onsil.flower.service.FlowerService;
import com.onsil.onsil.flower.dto.FlowerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/flowers")
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

    @GetMapping("/month/{month}/birth")
    public ResponseEntity<?> getBirthFlowers(@PathVariable int month) {
        try {
            return ResponseEntity.ok(flowerService.getBirthFlowersByMonth(month));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/month/{month}/recommend")
    public ResponseEntity<?> getRecommendedFlowers(@PathVariable int month) {
        try {
            return ResponseEntity.ok(flowerService.getRecommendedFlowersByMonth(month));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/month/{month}/list")
    public ResponseEntity<?> getFlowerList(@PathVariable int month) {
        try {
            return ResponseEntity.ok(flowerService.getFlowersByMonth(month));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

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
