package com.onsil.onsil.subscribe.controller;

import com.onsil.onsil.entity.Review;
import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.member.dto.MemberDto;
import com.onsil.onsil.review.repository.ReviewRepository;
import com.onsil.onsil.subscribe.dto.SubscribeDto;
import com.onsil.onsil.subscribe.repository.SubscribeRepository;
import com.onsil.onsil.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscribe")
public class SubscribeController {
    private final ReviewRepository reviewRepository;
    private final SubscribeService subscribeService;
    private final SubscribeRepository subscribeRepository;


    @GetMapping("/subscribe")
    public String subscPage(Model model, @AuthenticationPrincipal MemberDto memberDto) {
        List<SubscribeDto> subscribes = subscribeService.getRandom6Subscribes();
        model.addAttribute("subscribes", subscribes);
        return "subscribe/subscribe";
    }
    @GetMapping("/particular/{id}")
    public String particularProduct(@PathVariable Integer id, Model model) {
        SubscribeDto subscribeDto = subscribeService.getSubscribe(id);

        Subscribe subscribe = subscribeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("구독 상품을 찾을 수 없습니다."));

        List<Review> reviews = reviewRepository.findAllBySubscribe(subscribe);

        model.addAttribute("subscribe", subscribeDto);

        model.addAttribute("reviews", reviews);
        return "subscribe/particular";

    }
}
