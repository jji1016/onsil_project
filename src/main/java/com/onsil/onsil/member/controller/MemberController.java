package com.onsil.onsil.member.controller;

import com.onsil.onsil.member.dto.MemberDto;
import com.onsil.onsil.member.service.MemberService;
import com.onsil.onsil.subscribe.dao.SubscribeDao;
import com.onsil.onsil.subscribe.dto.SubscribeDto;
import com.onsil.onsil.subscribe.service.SubscribeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final SubscribeService subscribeService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginErrorMessage", "아이디, 비밀번호를 확인하세요.");
            return "member/login";
        }
        List<SubscribeDto> subscribes = subscribeService.getRandom6Subscribes();
        model.addAttribute("subscribes", subscribes);
        return "index/index";
    }
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute MemberDto memberDto, BindingResult bindingResult,
                         Model model) {
        if(bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("필드: " + error.getField());
                System.out.println("오류 메시지: " + error.getDefaultMessage());
            });
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "member/signup";
        }
        memberService.save(memberDto);

        return "redirect:/member/login";
    }

    // 아이디 중복 체크
    @GetMapping("/checkUserID")
    @ResponseBody
    public ResponseEntity<Boolean> checkUserID(@RequestParam String userID) {
        boolean isDuplicate = memberService.isUserIDDuplicate(userID);
        return ResponseEntity.ok(isDuplicate);
    }

    // 닉네임 중복 체크
    @GetMapping("/checkNickName")
    @ResponseBody
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        boolean isDuplicate = memberService.isNicknameDuplicate(nickname);
        return ResponseEntity.ok(isDuplicate);
    }

    // 이메일 중복 체크
    @GetMapping("/checkUserEmail")
    @ResponseBody
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean isDuplicate = memberService.isEmailDuplicate(email);
        return ResponseEntity.ok(isDuplicate);
    }
}