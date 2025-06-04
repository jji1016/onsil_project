package com.onsil.onsil.admin.repository;

import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminMemberRepository extends JpaRepository <Member, Integer>{


    int deleteByUserID(String userID);

    Member findByUserID(String userID);

    List<Member> findByUserNameContaining(String keyword);

}
