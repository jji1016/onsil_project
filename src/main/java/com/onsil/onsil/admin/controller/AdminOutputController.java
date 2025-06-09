package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.service.AdminOutputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminOutputController {

    private final AdminOutputService adminOutputService;

//    public AdminOutputController(AdminOutputService adminOutputService) {
//        this.adminOutputService = adminOutputService;
//    }

    //출고 목록 조히
//    @GetMapping("/outputlist")
//    public String outputList(Model model, @ModelAttribute AdminOutputDto adminOutputDto) {
//
//        List<AdminOutputDto> outputList = adminOutputService.searchOutputs(adminOutputDto.getFlowerName(),
//                adminOutputDto.getRegDate(),
//                adminOutputDto.getRegDate());
//        log.info("outputlist={}", outputList);
//        model.addAttribute("outputList", outputList);
//        return "admin/output";
//    }

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
}
