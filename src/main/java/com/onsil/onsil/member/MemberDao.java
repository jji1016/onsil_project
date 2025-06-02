package com.onsil.onsil.member;

import com.onsil.onsil.entity.Member;
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
}
