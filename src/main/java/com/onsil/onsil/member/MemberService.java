package com.onsil.onsil.member;

import com.onsil.onsil.constant.Role;
import com.onsil.onsil.entity.Member;
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
        memberDto.setRole(Role.ROLE_USER);
        Member savedMember = memberDto.toMember();
        memberDao.save(savedMember);
    }


}
