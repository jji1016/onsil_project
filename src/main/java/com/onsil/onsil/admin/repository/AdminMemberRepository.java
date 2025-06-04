package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminMemberRepository extends JpaRepository <Member, Integer>{


    int deleteByUserID(String userID);

    Member findByUserID(String userID);
}
