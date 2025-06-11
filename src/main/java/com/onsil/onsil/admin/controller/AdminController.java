package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminInputDto;
import com.onsil.onsil.admin.dto.AdminOrderListDto;
import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.dto.AdminStockDto;
import com.onsil.onsil.admin.service.AdminInputService;
import com.onsil.onsil.admin.service.AdminOrderListService;
import com.onsil.onsil.admin.service.AdminOutputService;
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
public class AdminController {

    private final AdminInputService adminInputService;

    private final AdminOutputService adminOutputService;

    private final AdminStockService adminStockService;

    private final AdminOrderListService adminOrderListService;

    //입고 검색 기능
    @GetMapping("/inputlist")
    public String inputList(@RequestParam(required = false) String category,
                            @RequestParam(required = false) String keyword,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                            Model model
    ) {log.info("category={}, keyword={}, startDate={}, endDate={}", category, keyword, startDate, endDate);
        List<AdminInputDto> inputList = adminInputService.searchInputs(category, keyword, startDate, endDate);
        model.addAttribute("inputList", inputList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "admin/input";
    }

    //출고 검색 기능
    @GetMapping("/outputlist")
    public String outputList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model
    ) {
        List<AdminOutputDto> outputList = adminOutputService.searchOutputs(category, keyword, startDate, endDate);
        model.addAttribute("outputList", outputList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "admin/output";
    }

    //재고 검색 기능
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
        model.addAttribute("stockList", stockList);
        return "admin/stock";
    }

    //주문내역 검색 기능
    @GetMapping("/orderlist")
    public String adminOrderList(@RequestParam(required = false) String userId,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate,
                                 Model model) {
        List<AdminOrderListDto> orderList = adminOrderListService.searchOrderlists(userId, status, startDate, endDate);
        model.addAttribute("orderList", orderList);
        return "admin/order";
    }
}
