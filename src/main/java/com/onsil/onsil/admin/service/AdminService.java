package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminDao;
import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.dto.SubscribeDto;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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

    public List<MemberDto> findByUserName(String keyword) {

        List<Member> foundFromUserName = adminDao.findByUserName(keyword);

        return foundFromUserName.stream()
                .map(member -> MemberDto.builder()
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
}
