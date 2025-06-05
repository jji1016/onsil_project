package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.service.AdminOutputService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/stockout")
public class AdminOutputController {

    private final AdminOutputService adminOutputService;

    public AdminOutputController(AdminOutputService adminOutputService) {
        this.adminOutputService = adminOutputService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                       Model model) {

        List<AdminOutputDto> list = adminOutputService.getStockOuts(
                start != null ? start : LocalDate.now().minusMonths(1),
                end != null ? end : LocalDate.now()
        );

        model.addAttribute("stockOutList", list);
        return "admin/stockout/list";
    }
}
