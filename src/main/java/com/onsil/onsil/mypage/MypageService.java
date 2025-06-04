package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final MypageDao mypageDao;

    public MemberDto findByUserID(String userID) {
        Optional<Member> optionalMember = mypageDao.findByUserID(userID);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member.toMemberDto();
        }else {
            return null;
        }
    }
}
