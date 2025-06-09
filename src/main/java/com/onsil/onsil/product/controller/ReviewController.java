package com.onsil.onsil.product.controller;

import com.onsil.onsil.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write")
    public String writeReview(@RequestParam("productId") int productId,
                              @RequestParam("rating") int rating,
                              @RequestParam("content") String content,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) throws IOException {
        try {
            reviewService.writeReview(productId, rating, content, imageFile);
        } catch (RuntimeException e) {
            // 중복 리뷰 예외 메시지 받아서 다시 상세페이지로 리다이렉트하며 전달
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/product/detail/" + productId;
        }
        return "redirect:/product/detail/" + productId;
    }
}