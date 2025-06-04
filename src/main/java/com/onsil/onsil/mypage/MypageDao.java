package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MypageDao {
    private final MypageMemberRepository mypageMemberRepository;

    public Optional<Member> findByUserID(String userID) {
        return mypageMemberRepository.findByUserID(userID);
    }
}
