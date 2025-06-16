package com.onsil.onsil.flower.controller;

import com.onsil.onsil.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flowers")
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

    // 월별 6개 (3*2)
    @GetMapping("/month/{month}")
    public ResponseEntity<?> getFlowersByMonth(@PathVariable int month) {
        return ResponseEntity.ok(flowerService.getFlowersByMonth(month));
    }

    // 상세
    @GetMapping("/{id}")
    public ResponseEntity<?> getFlowerDetail(@PathVariable Integer id) {
        var dto = flowerService.getFlowerDetail(id);
        if (dto == null) {
            return ResponseEntity.status(404).body("꽃 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(dto);
    }
}
