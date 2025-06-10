package com.onsil.onsil.product.controller;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.product.dto.ReviewDto;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.product.service.ProductService;
import com.onsil.onsil.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final ReviewService reviewService;


    @GetMapping("/list")
    public String list(Model model) {
        List<ProductDto> flowers=productService.findAll();
        model.addAttribute("flowers", flowers);
        return "product/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model,
                         @AuthenticationPrincipal UserDetails userDetails) {
        ProductDto flower = productService.getProductById(id);
        if (flower == null) {
            return "error/404";
        }

        model.addAttribute("flower", flower);

        if (userDetails != null) {
            String loginId = userDetails.getUsername();
            ReviewDto myReview = reviewService.findMyReview(loginId, id);
            if (myReview != null) {
                model.addAttribute("myReview", myReview);
            }
        }

        return "product/detail";
    }

}
