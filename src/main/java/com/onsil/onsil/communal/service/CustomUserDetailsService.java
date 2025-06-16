package com.onsil.onsil.communal.service;

import com.onsil.onsil.communal.dto.CustomUserDetails;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberDao memberDao;
    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        log.info("userID==={}",userID);
        Optional<Member> optionalMember = memberDao.findByUserID(userID);
        if(optionalMember.isPresent()) {
            return new CustomUserDetails(optionalMember.get());
        }
        throw new UsernameNotFoundException("아이디 패스워드 확인해 주세요");
    }
}
