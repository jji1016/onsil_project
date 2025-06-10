package com.onsil.onsil.product.controller;

import com.onsil.onsil.product.dto.ReviewDto;
import com.onsil.onsil.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write")
    public String writeReview(
                              @RequestParam("productId") int productId,
                              @RequestParam("rating") int rating,
                              @RequestParam("content") String content,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) throws IOException {
        try {
            reviewService.writeReview(productId, rating, content, imageFile);
        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/product/detail/" + productId;
        }
        return "redirect:/product/detail/" + productId;
    }
    @PostMapping("/delete")
    public String deleteReview(@RequestParam int reviewId,
                               @RequestParam int productId,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        try {
            reviewService.delete(reviewId);
        } catch (IllegalAccessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "본인 리뷰만 삭제할 수 있습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "리뷰 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/product/detail/" + productId;
    }
    @PostMapping("/edit")
    public String editReview(@ModelAttribute ReviewDto reviewDto,
                             @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        reviewService.updateReview(reviewDto, imageFile);

        return "redirect:/product/detail/" + reviewDto.getProductId();
    }
}