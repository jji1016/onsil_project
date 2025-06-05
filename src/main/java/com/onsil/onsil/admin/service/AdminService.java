package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminDao;
import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.dto.SubscribeDto;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Subscribe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminDao adminDao;

    public List<MemberDto> getAllMembers() {
        return adminDao.getAllMembers().stream()
                .filter(member -> member.getRole().equals("member"))
                .map(member ->
                        MemberDto.builder()
                                .zipcode(member.getZipcode())
                                .address01(member.getAddress01())
                                .address02(member.getAddress02())
                                .nickName(member.getNickName())
                                .tel(member.getTel())
                                .userID(member.getUserID())
                                .userName(member.getUserName())
                                .userEmail(member.getUserEmail())
                                .regDate(member.getRegdate())
                                .role(member.getRole())
                                .build())
                .toList();
    }

    public int deleteByUserID(String userID) {
        return adminDao.deleteByUserID(userID);
    }

    public MemberDto findByUserID(String userID) {

        Member member = adminDao.findByUserID(userID);
        log.info("member={}", member.toString());
        MemberDto dto = new MemberDto();

        dto.setId(member.getId());
        dto.setUserID(member.getUserID());
        dto.setUserName(member.getUserName());
        dto.setUserEmail(member.getUserEmail());
        dto.setNickName(member.getNickName());
        dto.setTel(member.getTel());
        dto.setAddress01(member.getAddress01());
        dto.setAddress02(member.getAddress02());
        dto.setZipcode(member.getZipcode());
        dto.setRegDate(member.getRegdate());

        return dto;
    }

    public List<SubscribeDto> findByMemberID(int id) {

        List<Subscribe> membersSubscribe = adminDao.findByMemberID(id);

        return membersSubscribe.stream()
                .map(subscribe -> SubscribeDto.builder()
                        .id(subscribe.getId())
                        .userID(subscribe.getMember().getUserID())
                        .productName(subscribe.getProduct().getFlowerName())
                        .orderNumber("250604160511")
                        .startDate(subscribe.getStartDate())
                        .endDate(subscribe.getEndDate())
                        .productPrice(subscribe.getProduct().getPrice())
                        .period(subscribe.getPeriod())
                        .build())
                .toList();
    }

    public List<MemberDto> search(String keyword, String category, LocalDateTime startDate, LocalDateTime endDate) {
        List<Member> members = adminDao.searchMembers(); // 전체 가져옴

        return members.stream()
                .filter(m -> {
                    if (keyword != null && !keyword.isBlank()) {
                        return switch (category) {
                            case "userID" -> m.getUserID().contains(keyword);
                            case "userName" -> m.getUserName().contains(keyword);
                            case "nickName" -> m.getNickName().contains(keyword);
                            default -> true;
                        };
                    }
                    return true;
                })
                .filter(m -> startDate == null || !m.getRegdate().isBefore(startDate))
                .filter(m -> endDate == null || !m.getRegdate().isAfter(endDate))
                .filter(m -> !m.getRole().equals("admin")) // 또는 m.getRole().equals("member")
                .map(m -> MemberDto.builder()
                        .zipcode(m.getZipcode())
                        .address01(m.getAddress01())
                        .address02(m.getAddress02())
                        .nickName(m.getNickName())
                        .tel(m.getTel())
                        .userID(m.getUserID())
                        .userName(m.getUserName())
                        .userEmail(m.getUserEmail())
                        .regDate(m.getRegdate())
                        .role(m.getRole())
                        .build())
                .toList();
    }

    @Transactional
    public void modifyMember(String userID, MemberDto dto) {
        Member member = adminDao.findByUserID(userID);

        member.updateInfo(
                dto.getNickName(),
                dto.getUserEmail(),
                dto.getZipcode(),
                dto.getAddress01(),
                dto.getAddress02()
        );
    }
}
