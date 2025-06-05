package com.onsil.onsil.member.repository;

import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUserID(String userID);

    boolean existsByUserID(String userID);          // id 중복 체크

    boolean existsByNickName(String nickName);  // 닉네임 중복 체크

    boolean existsByUserEmail(String userEmail);
}
