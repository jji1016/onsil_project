package com.onsil.onsil.member.service;

import com.onsil.onsil.constant.Role;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.dao.MemberDao;
import com.onsil.onsil.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void save(MemberDto memberDto) {

        if (memberDto.getUserID() == null || memberDto.getUserID().trim().isEmpty()) {
            throw new IllegalArgumentException("아이디는 필수 입력 사항입니다.");
        }
        if (memberDto.getUserPW() == null || memberDto.getUserPW().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력 사항입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(memberDto.getUserPW());
        memberDto.setUserPW(encodedPassword);
        memberDto.setRole(Role.ROLE_USER);
        Member savedMember = memberDto.toMember();
        memberDao.save(savedMember);
    }

    //아이디 중복 검사
    public boolean isUserIDDuplicate(String userID) {
        return memberDao.existsByUserID(userID);
    }

    // 닉네임 중복 검사
    public boolean isNicknameDuplicate(String nickname) {
        return memberDao.existsByNickName(nickname);
    }

    // 이메일 중복 검사
    public boolean isEmailDuplicate(String email) {
        return memberDao.existsByUserEmail(email);
    }
    public Member findByUserID(String userID) {
        return memberDao.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("해당 userID의 회원을 찾을 수 없습니다: " + userID));
    }
}
