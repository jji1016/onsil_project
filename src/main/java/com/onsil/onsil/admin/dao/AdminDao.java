package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminDao {

    private final MemberRepository memberRepository;
}
