package com.onsil.onsil.product.controller;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Value("${file.path}")
    private String upload;  // 여기서 주입

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
            return "error/404";
        }
        model.addAttribute("flower", flower);
        return "product/detail";
    }

}
