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
        String encodedPassword = bCryptPasswordEncoder.encode(memberDto.getUserPW());
        memberDto.setUserPW(encodedPassword);
<<<<<<< HEAD
        memberDto.setRole("ROLE_USER");
=======
        memberDto.setRole(Role.ROLE_USER);
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
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
<<<<<<< HEAD

=======
    public Member findByUserID(String userID) {
        return memberDao.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("해당 userID의 회원을 찾을 수 없습니다: " + userID));
    }
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
}
