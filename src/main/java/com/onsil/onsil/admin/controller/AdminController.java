package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.*;
import com.onsil.onsil.admin.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private final AdminProductService adminProductService;

    private final AdminSalesService adminSalesService;

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
    public String stocklist(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Integer maxQuantity,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            Model model
    ) {
        List<AdminStockDto> stockList = adminStockService.searchStocks(
                category, keyword, minQuantity, maxQuantity, minPrice, maxPrice
        );
        model.addAttribute("stockList", stockList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("minQuantity", minQuantity);
        model.addAttribute("maxQuantity", maxQuantity);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "admin/stock";
    }

    //주문내역 검색 기능
    @GetMapping("/orderlist")
    public String orderList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model
    ) {
        List<AdminOrderListDto> orderList = adminOrderListService.searchOrderLists(category, keyword, startDate, endDate);
        model.addAttribute("orderList", orderList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "admin/order";
    }

    //상품내역 검색 기능
    @GetMapping("/productlist")
    public String productList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        List<AdminProductDto> productList = adminProductService.searchProducts(category, keyword);
        model.addAttribute("productList", productList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        return "admin/product";
    }

    // 매출관리 페이지 진입
    @GetMapping("/sales")
    public String salesPage() {
        return "admin/sales";
    }

    // 통합 응답 (기간별+카테고리별)
    @GetMapping("/sales/dashboard")
    @ResponseBody
    public AdminSalesDashboardDto getSalesDashboard(
            @RequestParam String type,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return adminSalesService.getSalesDashboard(type, startDate, endDate);
    }
    }

