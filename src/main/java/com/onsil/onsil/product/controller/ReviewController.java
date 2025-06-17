package com.onsil.onsil.product.controller;

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
<<<<<<< HEAD
    public String writeReview(@RequestParam("productId") Integer productId, // int → Integer 변경
=======
    public String writeReview(@RequestParam("productId") int productId,
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
                              @RequestParam("rating") int rating,
                              @RequestParam("content") String content,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) throws IOException {
        try {
            reviewService.writeReview(productId, rating, content, imageFile);
        } catch (RuntimeException e) {
<<<<<<< HEAD
=======

>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/product/detail/" + productId;
        }
        return "redirect:/product/detail/" + productId;
    }
<<<<<<< HEAD

    @PostMapping("/delete")
    public String deleteReview(@RequestParam Integer reviewId, // int → Integer 변경
                               @RequestParam Integer productId, // int → Integer 변경
=======
    @PostMapping("/delete")
    public String deleteReview(@RequestParam int reviewId,
                               @RequestParam int productId,
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
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
<<<<<<< HEAD
}
=======
}
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
