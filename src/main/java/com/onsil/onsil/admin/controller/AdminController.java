package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.dto.PopularCountDto;
import com.onsil.onsil.admin.dto.SubscribeDto;
import com.onsil.onsil.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
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
}
