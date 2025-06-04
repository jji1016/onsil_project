package com.onsil.onsil.member.dao;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDao {
    private final MemberRepository memberRepository;

    public void save(Member savedMember) {
        memberRepository.save(savedMember);
    }

    public Optional<Member> findByUserID(String ID) {
        Optional<Member> optionalMember = memberRepository.findByUserID(ID);
        return optionalMember;
    }

    public boolean existsByUserID(String ID) {
        return memberRepository.existsByUserID(ID);
    }
    public boolean existsByNickName(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }
    public boolean existsByUserEmail(String userEmail) {
        return memberRepository.existsByUserEmail(userEmail);
    }
}
