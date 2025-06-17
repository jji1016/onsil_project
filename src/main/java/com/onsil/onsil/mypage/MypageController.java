package com.onsil.onsil.mypage;

import com.onsil.onsil.communal.dto.CustomUserDetails;
import com.onsil.onsil.member.dto.MemberDto;
import com.onsil.onsil.mypage.dto.*;
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

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId(); //member테이블 PK(memberID)
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
        log.info("loggedMemberDto: {}", loggedMemberDto);

        StatusCountDto statusCountDto = mypageService.statusCount(loggedMemberID);
        log.info("statusCountDto: {}", statusCountDto);

        model.addAttribute("loggedMemberDto", loggedMemberDto);
        model.addAttribute("statusCountDto", statusCountDto);
        return "mypage/mypage";
    }

    @PostMapping("/orderList") //주문내역 조회 및 검색
    @ResponseBody
    public Map<String, Object> orderList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                            @RequestBody SearchDto searchDto) {
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId();

        // 주문내역 전체 개수
        int totalItems = mypageService.countSearchOrderList(loggedMemberID, searchDto);

        int currentPage = searchDto.getCurrentPage() == 0 ? 1 : searchDto.getCurrentPage();
        int itemsPerPage = searchDto.getItemsPerPage() == 0 ? 5 : searchDto.getItemsPerPage();

        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        PageDto pageDto = PageDto.builder()
                .totalItems(totalItems)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .itemsPerPage(itemsPerPage)
                .build();

        List<MypageOrderListDto> orders  = mypageService.findSearchOrderList(loggedMemberID,searchDto, currentPage, itemsPerPage); //로그인한 사람의 주문내역

        Map<String, Object> map = new HashMap<>();
        map.put("orders", orders);
        map.put("pageDto", pageDto);

        return map;
    }

    @PostMapping("/subscribe") //정기배송 신청내역 조회
    @ResponseBody
    public  Map<String, Object> subscribe(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                          @RequestBody SearchDto searchDto) {
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId();

        int totalItems = mypageService.countSubscribeList(loggedMemberID);
        int itemsPerPage = searchDto.getItemsPerPage() == 0 ? 5 : searchDto.getItemsPerPage();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        //받아온 현재 페이지가 총 페이지수보다 클 경우 1
        int currentPage = searchDto.getCurrentPage() > totalPages ? 1 : searchDto.getCurrentPage();

        List<MypageSubscribeDto> mypageSubscribeDtoList = mypageService.findSubscribe(loggedMemberID,currentPage,itemsPerPage);
        log.info("mypageSubscribeDtoList: {}", mypageSubscribeDtoList);

        PageDto pageDto = PageDto.builder()
                .totalItems(totalItems)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .itemsPerPage(itemsPerPage)
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("subscribes", mypageSubscribeDtoList);
        map.put("pageDto", pageDto);

        return map;
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

    @PostMapping("/info") //회원 정보 조회
    @ResponseBody
    public MemberDto info(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들

        return loggedMemberDto;
    }

    @PostMapping("/modify")
    @ResponseBody
    public Map<String, String> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      @RequestBody Map<String, String> data) {
        Map<String, String> result = new HashMap<>();

        String currentPassword = data.get("currentPassword");
        String password = data.get("password"); // 새 비밀번호
        String password2 = data.get("password2"); // 새 비밀번호 확인
        String newEmail = data.get("userEmail");
        String newZipcode = data.get("zipcode");
        String newAddress01 = data.get("address01");
        String newAddress02 = data.get("address02");
        String newTel = data.get("tel");

        String userID = customUserDetails.getUsername();
        MemberDto loggedMemberDto = mypageService.findByUserID(userID);

        log.info("loggedMemberDto: {}", loggedMemberDto);
        // 비밀번호 검증
        if (password != null && !password.isBlank()) {
            if (!bCryptPasswordEncoder.matches(currentPassword, loggedMemberDto.getUserPW())) {
                result.put("isModify", "false");
                result.put("error", "현재 비밀번호가 올바르지 않습니다.");
                return result;
            }

            if (!password.equals(password2)) {
                result.put("isModify", "false");
                result.put("error", "비밀번호 확인이 일치하지 않습니다.");
                return result;
            }

            String encodeUserPW = bCryptPasswordEncoder.encode(password);
            loggedMemberDto.setUserPW(encodeUserPW);
        } else {
            // 비밀번호 변경 안 하는 경우 기존 비밀번호 유지
            loggedMemberDto.setUserPW(customUserDetails.getPassword());
        }

        if (newEmail != null) loggedMemberDto.setUserEmail(newEmail);
        if (newTel != null) loggedMemberDto.setTel(newTel);
        if (newZipcode != null) loggedMemberDto.setZipcode(newZipcode);
        if (newAddress01 != null) loggedMemberDto.setAddress01(newAddress01);
        if (newAddress02 != null) loggedMemberDto.setAddress02(newAddress02);
        log.info("modify_loggedMemberDto: {}", loggedMemberDto);
        mypageService.updateInfo(loggedMemberDto);

        result.put("isModify", "true");
        return result;
    }


}
