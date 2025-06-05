package com.onsil.onsil.flower.controller;

import com.onsil.onsil.flower.dto.FlowerDto;
import com.onsil.onsil.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flowers")
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

    @GetMapping("/{dataNo}")
    public FlowerDto getFlowerDetail(@PathVariable Integer dataNo, @RequestParam String serviceKey) throws Exception {
        return flowerService.getFlowerDetailFromApi(dataNo, serviceKey);
    }
}
