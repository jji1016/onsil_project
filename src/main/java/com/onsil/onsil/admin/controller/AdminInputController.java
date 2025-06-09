package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminInputDto;
import com.onsil.onsil.admin.service.AdminInputService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminInputController {

    private final AdminInputService adminInputService;

    public AdminInputController(AdminInputService adminInputService) {
        this.adminInputService = adminInputService;
    }

    //입고 목록 조회
    @GetMapping("/inputlist")
    public String inputList(Model model) {

        List<AdminInputDto> inputList = adminInputService.searchInputs();
        log.info("inputlist={}", inputList);
        model.addAttribute("inputList", inputList);
        return "admin/input";
    }

    //입고 검색 기능
    @GetMapping("/inputlist")
    public String inputList(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            Model model) {
        List<AdminInputDto> inputList = adminInputService.searchInputs(keyword, startDate, endDate);
        model.addAttribute("inputList", inputList);
        return "admin/input";
    }
}
