package com.onsil.onsil.config;

import com.onsil.onsil.constant.Role;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    //application시작할때 동작한다.
    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String admidID = "admin";
        Optional<Member> optionalMember = memberDao.findByUserID(admidID);
        if(!optionalMember.isPresent()) {
            Member adminMember = Member.builder()
                    .userID(admidID)
                    .role(Role.ROLE_ADMIN)
                    .userName("관리자")
                    .userEmail("admin@hanmail.net")
                    .nickName("adminNickName")
                    .tel("01087654321")
                    .address01("adminAddress01")
                    .zipcode("54321")
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .build();
            memberDao.save(adminMember);
        } else {
            System.out.println("관리자 계정이 이미 있습니다.");
        }
        Optional<Member> hongMember = memberDao.findByUserID("user");
        if(!hongMember.isPresent()) {
            Member member1 = Member.builder()
                    .userID("user")
                    .userName("userName")
                    .userEmail("user@naver.com")
                    .nickName("userNickName")
                    .tel("01012345678")
                    .address01("userAddress01")
                    .zipcode("12345")
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .role(Role.ROLE_USER)
                    .build();
            memberDao.save(member1);
        }

    }
}
