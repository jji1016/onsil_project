package com.onsil.onsil.mypage;

<<<<<<< HEAD
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.dto.MemberDto;
=======
import com.onsil.onsil.constant.Period;
import com.onsil.onsil.constant.Status;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.dto.MemberDto;
import com.onsil.onsil.mypage.dto.MypageOrderListDto;
import com.onsil.onsil.mypage.dto.MypageSubscribeDto;
import com.onsil.onsil.mypage.dto.SearchDto;
import com.onsil.onsil.mypage.dto.StatusCountDto;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
<<<<<<< HEAD
=======
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MypageService {
    private final MypageDao mypageDao;

    public MemberDto findByUserID(String userID) {
        Optional<Member> optionalMember = mypageDao.findByUserID(userID);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member.toMemberDto();
        }else {
            return null;
        }
    }

<<<<<<< HEAD
    public List<MypageOrderListDto> findOrderList(Integer loggedMemberID) {
        List<Object[]> ObjectOrderList = mypageDao.findOrderList(loggedMemberID);

        //Object타입을 MypageOrderListDto타입으로 변환시켜 리스트화
        List<MypageOrderListDto> MypageOrderListDto = ObjectOrderList.stream()
                .map(index -> new MypageOrderListDto(
                        ((Number) index[0]).intValue(),
                        (String) index[1],
                        ((Timestamp) index[2]).toLocalDateTime(),
                        (String) index[3],
                        ((Number) index[4]).intValue()
                ))
                .collect(Collectors.toList());
        return MypageOrderListDto;
    }

    public void updateInfo(MemberDto memberDto) {
        Member member = memberDto.toMember();
        mypageDao.save(member);
=======
    //검색된 주문내역 전체 개수
    public int countSearchOrderList(Integer loggedMemberID, SearchDto searchDto) {
        return mypageDao.countSearchOrderList(loggedMemberID, searchDto.getSearchInfo(), searchDto.getSearchYear());
    }

    //검색 및 연도를 기준으로 조회
    public List<MypageOrderListDto> findSearchOrderList(Integer loggedMemberID, SearchDto searchDto, int currentPage, int itemsPerPage) {
        String searchInfo = searchDto.getSearchInfo();
        String searchYear = searchDto.getSearchYear();
        int offset = (currentPage - 1) * itemsPerPage;

        List<Object[]> ObjectSearchOrderList = mypageDao.findSearchOrderList(
                loggedMemberID,
                searchInfo,
                searchYear,
                offset,
                itemsPerPage
        );

        //Object타입을 MypageOrderListDto타입으로 변환시켜 리스트화
        List<MypageOrderListDto> mypageSearchOrderListDtos = ObjectSearchOrderList.stream()
                .map(index -> new MypageOrderListDto(
                        ((Number) index[0]).intValue(),
                        Status.valueOf((String) index[1]).getLabel(),
                        ((Timestamp) index[2]).toLocalDateTime(),
                        (String) index[3],
                        ((Number) index[4]).intValue(),
                        (String) index[5]
                ))
                .collect(Collectors.toList());

        return mypageSearchOrderListDtos;
    }

    public List<MypageSubscribeDto> findSubscribe(Integer loggedMemberID, int currentPage, int itemsPerPage) {
        int offset = (currentPage - 1) * itemsPerPage;
        List<Object[]> ObjectMypageSubscribe = mypageDao.findSubscribe(loggedMemberID,offset,itemsPerPage);

        List<MypageSubscribeDto> mypageSubscribeDtoList = ObjectMypageSubscribe.stream()
                .map(index -> new MypageSubscribeDto(
                        ((Number) index[0]).intValue(),
                        Period.valueOf((String) index[1]).getLabel(),
                        ((Timestamp) index[2]).toLocalDateTime().toLocalDate(),
                        ((Timestamp) index[3]).toLocalDateTime().toLocalDate(),
                        (String) index[4],
                        (String) index[5],
                        ((Number) index[6]).intValue()
                ))
                .collect(Collectors.toList());
        return mypageSubscribeDtoList;
    }


    public void updateInfo(MemberDto memberDto) {
        Member member = mypageDao.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        member.updateInfo(
                memberDto.getUserPW(),
                memberDto.getUserEmail(),
                memberDto.getTel(),
                memberDto.getZipcode(),
                memberDto.getAddress01(),
                memberDto.getAddress02()
        );
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    }

    public int deleteAccount(Integer id) {
        return mypageDao.deleteAccount(id);
    }
<<<<<<< HEAD
=======

    public void deleteById(Integer id) {
        mypageDao.deleteById(id);
    }

    public int countSubscribeList(Integer loggedMemberID) {
        return mypageDao.countSubscribeList(loggedMemberID);
    }

    public StatusCountDto statusCount(Integer loggedMemberID) {
        List<Object[]> results  = mypageDao.statusCount(loggedMemberID);

        if (results .isEmpty()) {
            return new StatusCountDto(0, 0, 0, 0); // 값이 없을 경우 기본값 처리
        }

        Object[] objectStatusCount = results.get(0);

        return new StatusCountDto(
                objectStatusCount[0] != null ? ((Number) objectStatusCount[0]).intValue() : 0,
                objectStatusCount[1] != null ? ((Number) objectStatusCount[1]).intValue() : 0,
                objectStatusCount[2] != null ? ((Number) objectStatusCount[2]).intValue() : 0,
                objectStatusCount[3] != null ? ((Number) objectStatusCount[3]).intValue() : 0
        );
    }
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
}
