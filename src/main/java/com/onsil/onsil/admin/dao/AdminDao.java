package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.repository.AdminMemberRepository;
import com.onsil.onsil.admin.repository.AdminOrderListRepository;
import com.onsil.onsil.admin.repository.AdminSubscribeRepository;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.OrderList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    public List<OrderList> findByMemberID(int id) {
        return subscribeRepository.findByMember_MemberId(id);
    }
}
