package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {}
