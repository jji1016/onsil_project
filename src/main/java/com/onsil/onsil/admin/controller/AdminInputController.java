package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminInputDto;
import com.onsil.onsil.admin.service.AdminInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/stock")
@RequiredArgsConstructor
public class AdminInputController {

    private final AdminInputService adminInputService;


    public String viewStockEntry(@RequestParam(value = "keyword", required = false) String keyword,
                                 Model model) {
        List<AdminInputDto> entryList = adminInputService.getAllStockEntries(keyword);
        model.addAttribute("entryList", entryList);
        model.addAttribute("keyword", keyword);
        return "admin/stockEntry";
    }
}
