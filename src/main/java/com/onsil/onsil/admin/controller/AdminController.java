package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.service.AdminOutputService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminOutputService adminOutputService;



    @GetMapping("/outputlist")
    public String list(Model model) {

        List<AdminOutputDto> list = adminOutputService.getOutputs();
        log.info("list={}", list);

        model.addAttribute("outputList", list);
        return "admin/output";
    }
}
