package com.onsil.onsil.review.controller;

import com.onsil.onsil.communal.dto.CustomUserDetails;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.dao.MemberDao;
import com.onsil.onsil.member.repository.MemberRepository;
import com.onsil.onsil.member.service.MemberService;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.product.service.ProductService;
import com.onsil.onsil.review.service.ReviewService;
import com.onsil.onsil.subscribe.dto.SubscribeDto;
import com.onsil.onsil.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final SubscribeService subscribeService;
    private final ProductService productService;
    private final MemberService memberService;
    private final MemberDao memberDao;
    private final MemberRepository memberRepository;

    @GetMapping("/subscribe/{id}")
    public String writeSubscribeReview(@PathVariable int id, Model model,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        SubscribeDto subscribe = subscribeService.getSubscribe(id);
        String username = customUserDetails.getUsername();
        Member member = memberRepository.findByUserID(username)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        model.addAttribute("subscribeId", id);
        model.addAttribute("productId",subscribe.getProductId());
        model.addAttribute("productName",subscribe.getProductName());
        model.addAttribute("memberNickName",member.getNickName());
        return "review/subscribereview";
    }
    @GetMapping("/product/{id}")
    public String writeProductReview(@PathVariable int id, Model model,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ProductDto product = productService.getProductById(id);
        String username = customUserDetails.getUsername();
        Member member = memberRepository.findByUserID(username)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));


        model.addAttribute("productId", id);
        model.addAttribute("productName", product.getFlowerName());
        model.addAttribute("memberNickName", member.getNickName());
        return "review/productreview";
    }
    @PostMapping("subscribe/write")
    public String writeReview(@RequestParam("productId") int productId,
                              @RequestParam("rating") int rating,
                              @RequestParam("content") String content,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) throws IOException {
        try {
            reviewService.writeReview(productId, rating, content, imageFile);
        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/mypage/mypage/";
        }
        return "redirect:/mypage";
    }
}