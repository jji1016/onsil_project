package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.mypage.dto.MypageOrderListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MypageDao {
    private final MypageMemberRepository mypageMemberRepository;

    public Optional<Member> findByUserID(String userID) {
        return mypageMemberRepository.findByUserID(userID);
    }

    public List<Object[]> findOrderList(Integer loggedMemberID) {
        return mypageMemberRepository.findOrderList(loggedMemberID);
    }

    public void save(Member member) {
        mypageMemberRepository.save(member);
    }

    public int deleteAccount(Integer id) {
        return mypageMemberRepository.deleteAccount(id);
    }



    public List<Object[]> findSearchOrderList(Integer loggedMemberID, String category, String keyword, LocalDate startDate, LocalDate endDate) {
        return mypageMemberRepository.findSearchOrderList(loggedMemberID,category,keyword,startDate,endDate);
    }
}
