package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
<<<<<<< HEAD
import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.mypage.repository.MypageMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
=======
import com.onsil.onsil.mypage.dto.StatusCountDto;
import com.onsil.onsil.mypage.repository.MypageMemberRepository;
import com.onsil.onsil.mypage.repository.MypageSubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MypageDao {
    private final MypageMemberRepository mypageMemberRepository;
<<<<<<< HEAD
=======
    private final MypageSubscribeRepository mypageSubscribeRepository;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69

    public Optional<Member> findByUserID(String userID) {
        return mypageMemberRepository.findByUserID(userID);
    }

<<<<<<< HEAD
    public List<Object[]> findOrderList(Integer loggedMemberID) {
        return mypageMemberRepository.findOrderList(loggedMemberID);
    }

=======
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    public void save(Member member) {
        mypageMemberRepository.save(member);
    }

    public int deleteAccount(Integer id) {
        return mypageMemberRepository.deleteAccount(id);
    }
<<<<<<< HEAD
=======

    public List<Object[]> findSubscribe(Integer loggedMemberID, int offset, int itemsPerPage) {
        return mypageMemberRepository.findSubscribe(loggedMemberID,offset,itemsPerPage);
    }

    public void deleteById(Integer id) {
        mypageSubscribeRepository.deleteById(id);
    }

    public List<Object[]> findSearchOrderList(Integer loggedMemberID, String searchInfo, String searchYear, int offset, int itemsPerPage ) {
        return mypageMemberRepository.findSearchOrderList(loggedMemberID,searchInfo,searchYear,offset,itemsPerPage);
    }

    public int countSearchOrderList(Integer loggedMemberID, String searchInfo, String searchYear) {
        return mypageMemberRepository.countSearchOrderList(loggedMemberID,searchInfo,searchYear);
    }

    public int countSubscribeList(Integer loggedMemberID) {
        return mypageMemberRepository.countSubscribeList(loggedMemberID);
    }

    public List<Object[]> statusCount(Integer loggedMemberID) {
        return mypageMemberRepository.statusCount(loggedMemberID);
    }

    public Optional<Member> findById(Integer id) {
        return mypageMemberRepository.findById(id);
    }
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
}
