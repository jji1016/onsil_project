package com.onsil.onsil.home;

import com.onsil.onsil.member.dto.MemberDto;
import com.onsil.onsil.subscribe.dao.SubscribeDao;
import com.onsil.onsil.subscribe.dto.SubscribeDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/index")
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final SubscribeDao subscribeDao;

    @GetMapping("/index")
    public String index(Model model) {
        List<SubscribeDto> subscribes = subscribeDao.findRandom6Subscribes();
        log.info("subscribes 리스트 크기: {}", subscribes.size());  // 리스트 크기 출력
        subscribes.forEach(sub -> log.info("상품명: {}, 가격: {}", sub.getProductName(), sub.getPrice())); // 각 상품명과 가격 출력
        log.info("index() 진입함");
        model.addAttribute("subscribes", subscribes);
        return "index/index";
    }

}
