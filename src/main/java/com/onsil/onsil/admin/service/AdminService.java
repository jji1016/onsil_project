package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminDao;
import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.OrderList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<OrderList> findByMemberID(int id) {
        return adminDao.findByMemberID(id);
    }
}
