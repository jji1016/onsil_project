package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.repository.AdminMemberRepository;
import com.onsil.onsil.admin.repository.AdminOrderListRepository;
import com.onsil.onsil.admin.repository.AdminSubscribeRepository;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.entity.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminDao {

    private final AdminMemberRepository memberRepository;
    private final AdminOrderListRepository orderListRepository;
    private final AdminSubscribeRepository subscribeRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public int deleteByUserID(String userID) {
        return memberRepository.deleteByUserID(userID);
    }

    public Member findByUserID(String userID) {
        return memberRepository.findByUserID(userID);
    }

    public List<Subscribe> findByMemberID(int id) {
        return subscribeRepository.findByMember_id(id);
    }

    public List<Member> findByUserName(String keyword) {
        return memberRepository.findByUserNameContaining(keyword);
    }

    public List<Member> searchMembers(String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        return memberRepository.searchMembers(keyword,startDate,endDate);
    }
}
