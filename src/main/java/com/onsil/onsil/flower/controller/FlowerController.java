package com.onsil.onsil.flower.controller;

import com.onsil.onsil.flower.dto.FlowerDto;
import com.onsil.onsil.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FlowerController {

    private final FlowerService flowerService;

    /**
     * 꽃 정보 페이지 (기본 페이지)
     */
    @GetMapping("/flowers")
    public String flowersPage(Model model) {
        // 전체 월별 꽃 정보 조회
        Map<Integer, Map<String, Object>> allMonthlyFlowers = flowerService.getAllMonthlyFlowers();
        model.addAttribute("monthlyFlowers", allMonthlyFlowers);

        // 월별 꽃 개수 정보
        Map<Integer, Integer> monthlyCounts = flowerService.getAllMonthlyFlowerCounts();
        model.addAttribute("monthlyCounts", monthlyCounts);

        return "flowernote/flowernote"; // flowernote.html 템플릿 반환
    }

    /**
     * REST API - 특정 월의 꽃 정보 조회 (상세 정보 포함)
     */
    @GetMapping("/api/flowers/month/{month}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getFlowersByMonth(@PathVariable Integer month) {
        try {
            Map<String, Object> response = flowerService.getFlowersByMonth(month);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * REST API - 특정 월의 꽃 리스트만 조회 (단순)
     */
    @GetMapping("/api/flowers/list/{month}")
    @ResponseBody
    public ResponseEntity<List<FlowerDto>> getFlowerListByMonth(@PathVariable Integer month) {
        try {
            List<FlowerDto> response = flowerService.getFlowerListByMonth(month);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * REST API - 꽃 검색
     */
    @GetMapping("/api/flowers/search")
    @ResponseBody
    public ResponseEntity<List<FlowerDto>> searchFlowers(@RequestParam String keyword) {
        List<FlowerDto> results = flowerService.searchFlowers(keyword);
        return ResponseEntity.ok(results);
    }

    /**
     * REST API - 꽃 상세정보 조회
     */
    @GetMapping("/api/flowers/detail/{productId}")
    @ResponseBody
    public ResponseEntity<FlowerDto> getFlowerDetail(@PathVariable Integer productId) {
        try {
            FlowerDto flower = flowerService.getFlowerDetail(productId);
            return ResponseEntity.ok(flower);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * REST API - 전체 월별 꽃 정보 조회
     */
    @GetMapping("/api/flowers/all")
    @ResponseBody
    public ResponseEntity<Map<Integer, Map<String, Object>>> getAllMonthlyFlowers() {
        Map<Integer, Map<String, Object>> response = flowerService.getAllMonthlyFlowers();
        return ResponseEntity.ok(response);
    }

    /**
     * REST API - 월별 꽃 개수 조회
     */
    @GetMapping("/api/flowers/count/{month}")
    @ResponseBody
    public ResponseEntity<Integer> getFlowerCountByMonth(@PathVariable Integer month) {
        try {
            Integer count = flowerService.getFlowerCountByMonth(month);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * REST API - 전체 월별 꽃 개수 조회
     */
    @GetMapping("/api/flowers/counts")
    @ResponseBody
    public ResponseEntity<Map<Integer, Integer>> getAllMonthlyFlowerCounts() {
        Map<Integer, Integer> response = flowerService.getAllMonthlyFlowerCounts();
        return ResponseEntity.ok(response);
    }
}
