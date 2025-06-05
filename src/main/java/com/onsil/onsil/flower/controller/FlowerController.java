package com.onsil.onsil.flower.controller;

import com.onsil.onsil.flower.dto.FlowerDto;
import com.onsil.onsil.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/flowers")
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

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
                    .body(Map.of("error", "서버 오류 또는 외부 API 오류: " + e.getMessage()));
        }
    }
}
