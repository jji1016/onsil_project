package com.onsil.onsil.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsil.onsil.admin.dto.*;
import com.onsil.onsil.admin.dto.*;
import com.onsil.onsil.admin.service.*;
import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.dto.PopularCountDto;
import com.onsil.onsil.admin.dto.SubscribeDto;
import com.onsil.onsil.admin.service.AdminOutputService;
import com.onsil.onsil.admin.service.AdminService;
import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.mypage.dto.MypageOrderListDto;
import jakarta.servlet.http.HttpServletRequest;
import com.onsil.onsil.constant.Period;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.product.service.ProductService;
import com.onsil.onsil.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final AdminOutputService adminOutputService;
    private final SubscribeRepository subscribeRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Value("${file.path}products/")
    String productsPath;  // 여기서 주입

    @GetMapping("/admin")
    public String onsilHtml(Model model) throws JsonProcessingException {

        // 총 멤버수
        int countedMember = adminService.countAllMembers();
        // 한달이내 가입자 수(중복제거)
        int inOneMonthSubscribeMember = adminService.countOneMonth();
        // 일주일 내 리뷰 수
        int inOneWeekReview = adminService.inOneWeekReview();

        // 인기상품 리스트
        List<PopularCountDto> popularSubscribe = adminService.popularSubscribe();
        // 멤버리스트
        List<MemberDto> memberList = adminService.getAllMembers();
        // orderLists
        List<AdminOrderListDto> orderLists = adminService.getAllOrderLists();
        // subscribeLists
        List<SubscribeDto> subscribeList = adminService.getAllLists();

        // 오늘 주문건
        SubscribeSumDto todaySubscribe = adminService.subscribeToday();
        // 한달이내 주문건수
        SubscribeSumDto recentSubscribes = adminService.subscribeInOneMonth();
        // 배송현황
        DeliveryStatusDto statusSummary = adminService.getDeliveryStatusSummary();
        // 매출합계
        Map<String, BigDecimal> revenue = adminService.getMergedMonthlyRevenue();

        model.addAttribute("allSubscribeMember", countedMember);
        model.addAttribute("inOneMonthSubscribeMember", inOneMonthSubscribeMember);
        model.addAttribute("popularSubscribe", popularSubscribe);
        model.addAttribute("recentSubscribes", recentSubscribes.getList());
        model.addAttribute("todaySubscribe", todaySubscribe.getList());
        model.addAttribute("totalPrice", recentSubscribes.getTotalPrice());
        model.addAttribute("memberList", memberList);
        model.addAttribute("inOneWeekReview", inOneWeekReview);
        model.addAttribute("statusSummary", statusSummary);
        model.addAttribute("orderLists", orderLists);
        model.addAttribute("subscribeLists", subscribeList);
        model.addAttribute("monthlyLabels", revenue.keySet());
        model.addAttribute("monthlyData", revenue.values());

        return "admin/admin";
    }

    @GetMapping("/home")
    private String adminHome(Model model, HttpServletRequest request) {

        // 총 멤버수
        int countedMember = adminService.countAllMembers();
        // 한달이내 가입자 수(중복제거)
        int inOneMonthSubscribeMember = adminService.countOneMonth();
        // 일주일 내 리뷰 수
        int inOneWeekReview = adminService.inOneWeekReview();

        // 인기상품 리스트
        List<PopularCountDto> popularSubscribe = adminService.popularSubscribe();

        // 오늘 주문건
        SubscribeSumDto todaySubscribe = adminService.subscribeToday();
        // 한달이내 주문건수
        SubscribeSumDto recentSubscribes = adminService.subscribeInOneMonth();
        // 배송현황
        DeliveryStatusDto statusSummary = adminService.getDeliveryStatusSummary();
        // 매출합계
        Map<String, BigDecimal> revenue = adminService.getMergedMonthlyRevenue();

        model.addAttribute("allSubscribeMember", countedMember);
        model.addAttribute("inOneMonthSubscribeMember", inOneMonthSubscribeMember);
        model.addAttribute("popularSubscribe", popularSubscribe);
        model.addAttribute("recentSubscribes", recentSubscribes.getList());
        model.addAttribute("todaySubscribe", todaySubscribe.getList());
        model.addAttribute("totalPrice", recentSubscribes.getTotalPrice());
        model.addAttribute("inOneWeekReview", inOneWeekReview);
        model.addAttribute("statusSummary", statusSummary);
        model.addAttribute("monthlyLabels", revenue.keySet());
        model.addAttribute("monthlyData", revenue.values());

        return "admin/home"; // 전체 페이지 렌더
    }

    @GetMapping("member")
    public String adminMember(Model model, HttpServletRequest request) {
        // 멤버리스트
        List<MemberDto> memberList = adminService.getAllMembers();
        // orderLists
        List<AdminOrderListDto> orderLists = adminService.getAllOrderLists();
        // subscribeLists
        List<SubscribeDto> subscribeList = adminService.getAllLists();

        model.addAttribute("memberList", memberList);
        model.addAttribute("orderLists", orderLists);
        model.addAttribute("subscribeLists", subscribeList);

        return "admin/member"; // 전체 페이지 렌더
    }

    @GetMapping("/api/sales/monthly")
    @ResponseBody
    public List<SalesByMonthDto> getSalesData() {
        return adminService.getMonthlySales();
    }

    @PostMapping("/member-list/delete/{userID}")
    @ResponseBody
    public MemberDto delete(@PathVariable String userID) {
        return adminService.deleteByUserID(userID);
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
        return "redirect:/admin/member";
    }

    @GetMapping("/order-list/{id}")
    public String orderList(@PathVariable int id, Model model) {


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

//    private final AdminOutputService adminOutputService;

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
        List<AdminOutputDto> outputList = adminOutputService.searchOutputs(category, keyword, startDate, endDate, page, pageSize);
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


        log.info("con category == {}", category);
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
        List<AdminOrderDto> orderList = adminOrderListService.searchOrderLists(category, keyword, startDate, endDate, page, pageSize);

        model.addAttribute("orderList", orderList);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "admin/order02";
    }

    @GetMapping("/product")
    public String product(Model model) {

        return "admin/product";
    }

    @PostMapping("/save")
    public String saveProduct(
            @RequestParam("f_month") int fMonth,
            @RequestParam("flowerName") String flowerName,
            @RequestParam("price") int price,
            @RequestParam("flowerInfo") String flowerInfo,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("f_lang") String flowLang,
            @RequestParam("f_use") String fUse,
            @RequestParam("f_grow") String fGrow,
            @RequestParam("f_type") String fType
    ) throws IOException {

        File saveDir = new File(productsPath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String storedFileName = baseName + "_" + timestamp + extension; // abc_20250605123045.jpg


        String savePath = productsPath + storedFileName;


        imageFile.transferTo(new File(savePath));


        Product product = new Product();
        product.setFMonth(fMonth);
        product.setFlowerName(flowerName);
        product.setImage(storedFileName);
        product.setFlowLang(flowLang);
        product.setFUse(fUse);
        product.setFGrow(fGrow);
        product.setFType(fType);
        product.setFlowerInfo(flowerInfo);
        product.setPrice(price);

        productRepository.save(product);


        return "redirect:/admin/product";
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




