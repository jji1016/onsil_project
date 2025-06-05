package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.dto.SubscribeDto;
import com.onsil.onsil.admin.service.AdminService;
import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.entity.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String index() {

        List<MemberDto> memberList = adminService.getAllMembers();
//        List<OrderListDto> orderLists = adminService.getAllOrders();

        return "admin/dashboard";
    }

    @GetMapping("/member-list")
    public String memberList(Model model) {
        List<MemberDto> memberList = adminService.getAllMembers();
        model.addAttribute("memberList", memberList);

        return "admin/member-list";
    }

    @DeleteMapping("/member-list/{userID}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable String userID) {

        int result = adminService.deleteByUserID(userID);
        Map<String, Object> resultMap = new HashMap<>();

        if (result > 0) {
            resultMap.put("isDelete", true);
        } else {
            resultMap.put("isDelete", false);
        }
        return resultMap;
    }

    @GetMapping("/member-detail/{userID}")
    public String memberDetail(@PathVariable String userID, Model model) {

        MemberDto memberDetail = adminService.findByUserID(userID);
        model.addAttribute("memberDetail", memberDetail);

        return "admin/member-detail";
    }

    @GetMapping("/member-modify/{userID}")
    public String memberModify(@PathVariable String userID, Model model) {

        MemberDto memberModify = adminService.findByUserID(userID);
        model.addAttribute("memberModify", memberModify);

        return "admin/member-modify";
    }

    @PostMapping("/member-modify/{id}")
    public String modifyMember(@PathVariable Long id, @ModelAttribute MemberDto dto) {
        adminService.modifyMember(id, dto);
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
}
