package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.dto.PopularCountDto;
import com.onsil.onsil.admin.repository.AdminMemberRepository;
import com.onsil.onsil.admin.repository.AdminOrderListRepository;
import com.onsil.onsil.admin.repository.AdminSubscribeRepository;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminDao {

    private final AdminMemberRepository memberRepository;
    private final AdminOrderListRepository orderListRepository;
    private final AdminSubscribeRepository subscribeRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member findByUserID(String userID) {
        return memberRepository.findByUserID(userID);
    }

    public List<Subscribe> findByMemberID(int id) {
        return subscribeRepository.findByMember_id(id);
    }

    //    public List<Member> searchMembers(String keyword, String category, LocalDateTime startDate, LocalDateTime endDate) {
//        return memberRepository.searchMembers(keyword, category, startDate,endDate);
//    }
    public List<Member> searchMembers() {
        return memberRepository.findAll();
    }

    public int countAllMembers() {
        return subscribeRepository.countDistinctNumber();
    }

    public int countOneMonth(LocalDateTime todayDate) {
        return subscribeRepository.countOneMonthMember(todayDate);
    }

    public List<PopularCountDto> popularSubscribe() {
        return subscribeRepository.popularSubscribe(PageRequest.of(0, 5));
    }

    public List<Subscribe> findRecentInMonth(LocalDateTime oneMonthAgo, Pageable topFive) {
        return  subscribeRepository.findRecentInMonth(oneMonthAgo, topFive);
    }
}
