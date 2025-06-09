package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminStockDto;
import com.onsil.onsil.admin.service.AdminStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminStockController {

    private final AdminStockService adminStockService;

    @GetMapping("/stocklist")
    public String stocklist(@RequestParam(required = false) String flowerName,
                            @RequestParam(required = false) Integer minPrice,
                            @RequestParam(required = false) Integer maxPrice,
                            @RequestParam(required = false) Integer minStock,
                            @RequestParam(required = false) Integer maxStock,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate,
                            Model model) {
        List<AdminStockDto> stockList = adminStockService.searchStocks(flowerName, minPrice, maxPrice, minStock, maxStock, startDate, endDate);
        return "admin/stock:";
    }
}
