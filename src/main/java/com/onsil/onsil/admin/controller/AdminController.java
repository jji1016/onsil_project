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

//    public AdminInputController(AdminInputService adminInputService) {
//        this.adminInputService = adminInputService;
//    }

    //입고 목록 조회
//    @GetMapping("/inputlist")
//    public String inputList(Model model,@ModelAttribute AdminInputDto adminInputDto) {
//
//        List<AdminInputDto> inputList = adminInputService.searchInputs(key);
//        log.info("inputlist={}", inputList);
//        model.addAttribute("inputList", inputList);
//        return "admin/input";
//    }

    //입고 검색 기능
    @GetMapping("/inputlist")
    public String inputList(@RequestParam(required = false) String flowerName,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate,
                            Model model) {
        List<AdminInputDto> inputList = adminInputService.searchInputs(flowerName, startDate, endDate);
//        model.addAttribute("inputList", inputList);
        return "admin/input";
    }
    //출고 검색 기능
    @GetMapping("/outputlist")
    public String outputList(@RequestParam(required = false) String flowerName,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate,
                             Model model) {
        List<AdminOutputDto> outputList = adminOutputService.searchOutputs(flowerName, startDate, endDate);
//        model.addAttribute("outputList", outputList);
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
        return "admin/stock";
    }

    //주문내역 검색 기능
    @GetMapping("/orderlist")
    public String adminOrderList(@RequestParam(required = false) String userId,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate,
                                 Model model){
        List<AdminOrderListDto> orderList = adminOrderListService.searchOrderlists(userId, status, startDate, endDate);
        return "admin/orderlist";
    }
}
