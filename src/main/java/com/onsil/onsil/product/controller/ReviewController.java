package com.onsil.onsil.product.controller;

import com.onsil.onsil.product.dto.ReviewDto;
import com.onsil.onsil.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(value = "/write", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> writeReview(@RequestParam("productId") int productId,
                                         @RequestParam("rating") int rating,
                                         @RequestParam("content") String content,
                                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        try {
            reviewService.writeReview(productId, rating, content, imageFile);
            return ResponseEntity.ok(new ApiResponse(true, "리뷰가 작성되었습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteReview(@RequestParam int reviewId
                                          ) {
        try {
            reviewService.delete(reviewId);
            return ResponseEntity.ok(new ApiResponse(true, "리뷰가 삭제되었습니다."));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).body(new ApiResponse(false, "본인 리뷰만 삭제할 수 있습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "리뷰 삭제 중 오류가 발생했습니다."));
        }
    }

    @PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> editReview(@RequestParam("reviewId") int reviewId,
                                        @RequestParam("productId") int productId,
                                        @RequestParam("rating") int rating,
                                        @RequestParam("content") String content,
                                        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        try {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(reviewId);
            reviewDto.setProductId(productId);
            reviewDto.setRating(rating);
            reviewDto.setContent(content);

            reviewService.updateReview(reviewDto, imageFile);
            return ResponseEntity.ok(new ApiResponse(true, "리뷰가 수정되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "리뷰 수정 중 오류가 발생했습니다."));
        }
    }

    public static class ApiResponse {
        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}