package com.onsil.onsil.member.controller;

import com.onsil.onsil.member.dto.MemberDto;
import com.onsil.onsil.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "member/signup";
        }
        memberService.save(memberDto);

        return "member/login";
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


