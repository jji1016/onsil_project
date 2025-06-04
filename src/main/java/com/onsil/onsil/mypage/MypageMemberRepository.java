package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MypageMemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByUserID(String userID);
}
