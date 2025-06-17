package com.onsil.onsil.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsil.onsil.admin.dto.*;
import com.onsil.onsil.admin.service.AdminOutputService;
import com.onsil.onsil.admin.service.AdminService;
import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.mypage.dto.MypageOrderListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final AdminOutputService adminOutputService;

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
        model.addAttribute("todaySubscribe",todaySubscribe.getList());
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
        return "redirect:admin/admin";
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




    @GetMapping("/outputlist")
    public String list(Model model) {

        List<AdminOutputDto> list = adminOutputService.getOutputs();
        log.info("list={}", list);

        model.addAttribute("outputList", list);
        return "admin/output";
    }
}
