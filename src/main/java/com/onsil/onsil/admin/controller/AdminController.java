package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminOrderListDto;
import com.onsil.onsil.admin.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminOrderService adminOrderService;

    @GetMapping("/admin/orders")
    public String getOrderListByUserId(@RequestParam("userId") String userId, Model model) {
        List<AdminOrderListDto> adminOrderListDtoList = adminOrderService.getOrderListByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("orderList", adminOrderListDtoList);
        return "/admin/index";
    }

}
