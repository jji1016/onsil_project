package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMemberRepository extends JpaRepository<Member, Integer> {


    Member findByUserID(String userID);

    List<Member> findByUserNameContaining(String keyword);


//    @Query("SELECT m FROM Member m WHERE " +
//            "(:keyword IS NULL OR LOWER(m.userName) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
//            "(:startDate IS NULL OR m.regdate >= :startDate) AND " +
//            "(:endDate IS NULL OR m.regdate <= :endDate)")
//    List<Member> searchMembers(@Param("keyword") String keyword,
//                               @Param("category") String category,
//                               @Param("startDate") LocalDateTime startDate,
//                               @Param("endDate") LocalDateTime endDate);
}
