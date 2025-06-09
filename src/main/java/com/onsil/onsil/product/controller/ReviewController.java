package com.onsil.onsil.product.controller;

import com.onsil.onsil.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write")
    public String writeReview(@RequestParam("productId") int productId,
                              @RequestParam("rating") int rating,
                              @RequestParam("content") String content) {
        reviewService.writeReview(productId, rating, content);
        return "redirect:/product/detail/" + productId;
    }
}