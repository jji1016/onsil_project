package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.*;
import com.onsil.onsil.admin.service.*;
import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.dto.PopularCountDto;
import com.onsil.onsil.admin.dto.SubscribeDto;
import com.onsil.onsil.admin.service.AdminOutputService;
import com.onsil.onsil.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String index(Model model) {

        int countedMember = adminService.countAllMembers();
        int inOneMonthSubscribeMember = adminService.countOneMonth();
        List<PopularCountDto> popularSubscribe = adminService.popularSubscribe();
        List<SubscribeDto> recentSubscribes = adminService.findRecentSubscribeInOneMonth();

        model.addAttribute("allSubscribeMember", countedMember);
        model.addAttribute("inOneMonthSubscribeMember", inOneMonthSubscribeMember);
        model.addAttribute("popularSubscribe", popularSubscribe);
        model.addAttribute("recentSubscribes", recentSubscribes);


        return "admin/dashboard";
    }

    @GetMapping("/member-list")
    public String memberList(Model model) {

        List<MemberDto> memberList = adminService.getAllMembers();
        model.addAttribute("memberList", memberList);

        return "admin/member-list";
    }

    @PostMapping("/member-list/delete/{userID}")
    @ResponseBody
    public MemberDto delete(@PathVariable String userID) {
        return adminService.deleteByUserID(userID);
    }

    @GetMapping("/member-detail/{userID}")
    public String memberDetail(@PathVariable String userID, Model model) {

        MemberDto memberDetail = adminService.findByUserID(userID);
        model.addAttribute("memberDetail", memberDetail);

        return "admin/member-detail";
    }

    @GetMapping("/member-modify/{userID}")
    public String modifyMember(@PathVariable String userID, Model model) {

        MemberDto memberModify = adminService.findByUserID(userID);
        model.addAttribute("memberModify", memberModify);

        return "admin/member-modify";
    }

    @PostMapping("/member-modify/{userID}")
    public String modifyMember(@PathVariable String userID, @ModelAttribute MemberDto dto) {
        adminService.modifyMember(userID, dto);
        return "redirect:/admin/member-list";
    }


    @GetMapping("/order-list/{id}")
    public String orderList(@PathVariable int id, Model model) {

        List<SubscribeDto> orderLists = adminService.findByMemberID(id);
        model.addAttribute("orderLists", orderLists);

        return "admin/order-list";
    }

    @GetMapping("/member-search")
    @ResponseBody
    public List<MemberDto> searchMembers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        return adminService.search(keyword, category, start, end);
    }

    private final AdminInputService adminInputService;

    private final AdminOutputService adminOutputService;

    private final AdminStockService adminStockService;

    private final AdminOrderListService adminOrderListService;

    private final AdminProductService adminProductService;

    private final AdminSalesService adminSalesService;

    //입고 검색 기능
    @GetMapping("/inputlist")
    //@ResponseBody
    public String inputList(@RequestParam(required = false) String category,
                            @RequestParam(required = false) String keyword,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "10") int pageSize,
                            Model model
    ) {
        int totalCount = adminInputService.countInputs(category, keyword, startDate, endDate);
        model.addAttribute("totalCount", totalCount);
        log.info("category={}, keyword={}, startDate={}, endDate={}", category, keyword, startDate, endDate);
        List<AdminInputDto> inputList = adminInputService.searchInputs(category, keyword, startDate, endDate, page, pageSize);

        model.addAttribute("inputList", inputList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);

        return "admin/input01";
    }

//    @GetMapping("/inputlistjson")
//    @ResponseBody
//    //@ResponseBody
//    public List<AdminInputDto> inputListJson(@RequestParam(required = false) String category,
//                                             @RequestParam(required = false) String keyword,
//                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
//                                             @RequestParam(required = false, defaultValue = "1") int page,
//                                             @RequestParam(required = false, defaultValue = "10") int pageSize,
//                                             Model model
//    ) {
//        log.info("category={}, keyword={}, startDate={}, endDate={}", category, keyword, startDate, endDate);
//        List<AdminInputDto> inputList = adminInputService.searchInputs(category, keyword, startDate, endDate,page, pageSize);
//
//        model.addAttribute("inputList", inputList);
//        model.addAttribute("category", category);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("startDate", startDate);
//        model.addAttribute("endDate", endDate);
//
//        return inputList;
//    }


    //출고 검색 기능
    @GetMapping("/outputlist")
    public String outputList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        int totalCount = adminOutputService.countOutputs(category, keyword, startDate, endDate);
        model.addAttribute("totalCount", totalCount);
        List<AdminOutputDto> outputList = adminOutputService.searchOutputs(category, keyword, startDate, endDate,  page, pageSize);
        model.addAttribute("outputList", outputList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "admin/output01";
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
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        int totalCount = adminStockService.countStocks(category, keyword, minQuantity, maxQuantity, minPrice, maxPrice);
        model.addAttribute("totalCount", totalCount);


        log.info("con category == {}",category);
        List<AdminStockDto> stockList = adminStockService.searchStocks(
                category, keyword, minQuantity, maxQuantity, minPrice, maxPrice, page, pageSize
        );
        log.info("stockList == {}", stockList);
        model.addAttribute("stockList", stockList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("minQuantity", minQuantity);
        model.addAttribute("maxQuantity", maxQuantity);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);

        return "admin/stock01";
    }

    //주문내역 검색 기능
    @GetMapping("/orderlist")
    public String orderList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        int totalCount = adminOrderListService.countOrderLists(category, keyword, startDate, endDate);
        model.addAttribute("totalCount", totalCount);
        List<AdminOrderListDto> orderList = adminOrderListService.searchOrderLists(category, keyword, startDate, endDate, page, pageSize);

        model.addAttribute("orderList", orderList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "admin/order02";
    }

    //상품내역 검색 기능
    @GetMapping("/productlist")
    public String productList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,


            Model model
    ) {
        int totalCount = adminProductService.countProducts(category, keyword, minPrice, maxPrice);
        model.addAttribute("totalCount", totalCount);
        List<AdminProductDto> productList = adminProductService.searchProducts(category, keyword, minPrice, maxPrice, page, pageSize);

        model.addAttribute("productList", productList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "admin/product02";
    }

    // 매출관리 페이지 진입
    @GetMapping("/sales")
    public String salesPage() {
        return "admin/sales01";
    }

    // 통합 응답 (기간별+카테고리별)
    @GetMapping("/sales/dashboard")
    @ResponseBody
    public AdminSalesDashboardDto getSalesDashboard(
            @RequestParam String type) {
        return adminSalesService.getSalesDashboard(type);
    }

}

