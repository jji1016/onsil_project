package com.onsil.onsil.product.controller;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/list")
    public String list(Model model) {
        List<ProductDto> flowers=productService.findAll();
        model.addAttribute("flowers", flowers);
        return "product/list";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        ProductDto flower = productService.getProductById(id);
        if (flower == null) {
            return "error/404"; // 존재하지 않는 경우 예외 처리 가능
        }
        model.addAttribute("flower", flower);
        return "product/detail";
    }

}
