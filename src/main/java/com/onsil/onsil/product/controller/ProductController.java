package com.onsil.onsil.product.controller;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Review;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.review.repository.ReviewRepository;
import com.onsil.onsil.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;



    @GetMapping("/list")
    public String list(Model model) {
        List<ProductDto> flowers=productService.findRandom8();
        model.addAttribute("flowers", flowers);
        return "product/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        ProductDto flower = productService.getProductById(id);
        if (flower == null) {
            return "error/404";
        }
        Product product = productRepository.findById(flower.getId()).orElse(null);

        List<Review> reviews = reviewRepository.findAllByProduct(product);
        model.addAttribute("flower", flower);
        model.addAttribute("reviews", reviews);
        return "product/detail";
    }

}
