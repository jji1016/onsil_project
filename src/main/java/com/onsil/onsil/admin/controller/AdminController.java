package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.service.AdminOutputService;
import lombok.RequiredArgsConstructor;
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
public class AdminOutputController {

    private final AdminOutputService adminOutputService;



    @GetMapping("/outputlist")
    public String list(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                       Model model) {

        List<AdminOutputDto> list = adminOutputService.getOutputs();


        model.addAttribute("outputList", list);
        return "admin/output";
    }
}
