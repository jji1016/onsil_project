package com.onsil.onsil.member.repository;

import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
=======

>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUserID(String userID);
<<<<<<< HEAD
    boolean existsByUserID(String userID);          // id 중복 체크
    boolean existsByNickName(String nickName);  // 닉네임 중복 체크
    boolean existsByUserEmail(String userEmail);
=======

    boolean existsByUserID(String userID);          // id 중복 체크

    boolean existsByNickName(String nickName);  // 닉네임 중복 체크

    boolean existsByUserEmail(String userEmail);

    Optional<Member> findByUserName(String userName);
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
}
