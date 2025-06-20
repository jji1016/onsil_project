package com.onsil.onsil.member.repository;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUserID(String userID);

    boolean existsByUserID(String userID);          // id 중복 체크

    boolean existsByNickName(String nickName);  // 닉네임 중복 체크

    boolean existsByUserEmail(String userEmail);

    Optional<Member> findByNickName(String NickName);



    List<Member> id(Integer id);
}
