package com.onsil.onsil.mypage;

import com.onsil.onsil.communal.dto.CustomUserDetails;
import com.onsil.onsil.member.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        log.info("mypage userID==={}", userID);
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
        log.info("loggedMemberDto==={}", loggedMemberDto);
        model.addAttribute("loggedMemberDto", loggedMemberDto);

        return "mypage/home";
    }
}
