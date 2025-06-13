package com.onsil.onsil.mypage;

import com.onsil.onsil.communal.dto.CustomUserDetails;
import com.onsil.onsil.member.dto.MemberDto;
import com.onsil.onsil.mypage.dto.MypageOrderListDto;
import com.onsil.onsil.mypage.dto.MypageSubscribeDto;
import com.onsil.onsil.mypage.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @GetMapping("/home")
//    public String home(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
//        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
//
//        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
//        model.addAttribute("loggedMemberDto", loggedMemberDto);
//
//        Integer loggedMemberID = loggedMemberDto.getId(); //Member 테이블의 기본키
//        List<MypageOrderListDto> mypageOrderListDto = mypageService.findOrderList(loggedMemberID); //로그인한 사람의 주문내역
//        log.info("mypageOrderListDto: {}", mypageOrderListDto);
//        model.addAttribute("mypageOrderListDto", mypageOrderListDto);
//
//        return "mypage/home";
//    }
//
//    @GetMapping("/info") //회원 상세 정보 페이지
//    public String info(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
//        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
//        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
//        log.info("loggedMemberDto: {}", loggedMemberDto);
//        model.addAttribute("loggedMemberDto", loggedMemberDto);
//        return "mypage/info";
//    }
//
//    @PostMapping("/modify") //회원 정보 수정
//    public String modify(@ModelAttribute MemberDto memberDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
//        if (memberDto.getUserPW() == null || memberDto.getUserPW().isEmpty()) { //비밀번호 수정 안할시
//            memberDto.setUserPW(customUserDetails.getPassword());
//        }else{ //비밀번호 수정시 받아서 암호화
//            String userPW = memberDto.getUserPW();
//            String encodeUserPW = bCryptPasswordEncoder.encode(userPW);
//            log.info("encodeUserPW: {}", encodeUserPW);
//            memberDto.setUserPW(encodeUserPW);
//        }
//        mypageService.updateInfo(memberDto);
//        return "redirect:/mypage/info";
//    }
//
//
//    @GetMapping("/subscribe") //정기배송 신청내역 조회
//    public String subscribe(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
//        Integer loggedMemberID = customUserDetails.getLoggedMember().getId();
//        List<MypageSubscribeDto> mypageSubscribeDtoList = mypageService.findSubscribe(loggedMemberID);
//        log.info("mypageSubscribeDtoList: {}", mypageSubscribeDtoList);
//        model.addAttribute("mypageSubscribeDtoList", mypageSubscribeDtoList);
//
//        return "mypage/subscribe";
//    }



    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
        Integer loggedMemberID = loggedMemberDto.getId(); //Member 테이블의 기본키
        log.info("loggedMemberDto: {}", loggedMemberDto);

        //주문내역 조회
        List<MypageOrderListDto> mypageOrderListDto = mypageService.findOrderList(loggedMemberID);
        log.info("mypageOrderListDto: {}", mypageOrderListDto);

        //정기배송 신청내역 조회
        List<MypageSubscribeDto> mypageSubscribeDtoList = mypageService.findSubscribe(loggedMemberID);
        log.info("mypageSubscribeDtoList: {}", mypageSubscribeDtoList);

        model.addAttribute("mypageSubscribeDtoList", mypageSubscribeDtoList);
        model.addAttribute("mypageOrderListDto", mypageOrderListDto);
        model.addAttribute("loggedMemberDto", loggedMemberDto);
        return "mypage/mypage";
    }

    @GetMapping("/orderList") //주문내역 조회
    public String orderList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                            @ModelAttribute SearchDto searchDto,
                            Model model) {
        log.info("searchDto: {}", searchDto);
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId();
        log.info("orderlist-loggedMemberID: {}", loggedMemberID);

        List<MypageOrderListDto> mypageOrderListDto = mypageService.findSearchOrderList(loggedMemberID,searchDto); //로그인한 사람의 주문내역
        log.info("orderlist-mypageOrderListDto: {}", mypageOrderListDto);
        model.addAttribute("mypageOrderListDto", mypageOrderListDto);

        return "mypage/mypage";
    }

    @GetMapping("/delete/{id}") //회원 탈퇴
    @ResponseBody
    public Map<String,String> deleteAccount(@PathVariable("id") Integer id) {
        int result = mypageService.deleteAccount(id);

        Map<String,String> map = new HashMap<>();
        if (result == 1) {
            map.put("isDelete", "true");
        }
        return map;
    }

    @GetMapping("/cancelSubscribe/{id}") //정기배송 구독 취소
    @ResponseBody
    public Map<String,String> cancelSubscribe(@PathVariable Integer id) {
        mypageService.deleteById(id);

        Map<String,String> map = new HashMap<>();
        map.put("isdelete", "true");
        return map;
    }

}
