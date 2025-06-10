package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.CategorySalesDto;
import com.onsil.onsil.admin.dto.DailySalesDto;
import com.onsil.onsil.admin.dto.SalesSummaryDto;
import com.onsil.onsil.admin.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SalesController {


    private final SalesService salesService;

    // 요약 정보
    @GetMapping("/summary")
    public SalesSummaryDto getSalesSummary() {
        return salesService.getSalesSummary();
    }

    // 일별 매출
    @GetMapping("/daily")
    public List<DailySalesDto> getDailySales() {
        return salesService.getDailySales();
    }

    // 카테고리별 매출
    @GetMapping("/category")
    public List<CategorySalesDto> getCategorySales() {
        return salesService.getCategorySales();
    }
}
