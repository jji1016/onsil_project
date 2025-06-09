package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.dto.MemberDto;
import com.onsil.onsil.mypage.dto.MypageOrderListDto;
import com.onsil.onsil.mypage.dto.MypageSubscribeDto;
import com.onsil.onsil.mypage.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<MypageOrderListDto> findOrderList(Integer loggedMemberID) {
        List<Object[]> ObjectOrderList = mypageDao.findOrderList(loggedMemberID);

        //Object타입을 MypageOrderListDto타입으로 변환시켜 리스트화
        List<MypageOrderListDto> mypageOrderListDto = ObjectOrderList.stream()
                .map(index -> new MypageOrderListDto(
                        ((Number) index[0]).intValue(),
                        (String) index[1],
                        ((Timestamp) index[2]).toLocalDateTime(),
                        (String) index[3],
                        ((Number) index[4]).intValue(),
                        (String) index[5]
                ))
                .collect(Collectors.toList());
        return mypageOrderListDto;
    }

    //검색 및 기간을 기준으로 조회
    public List<MypageOrderListDto> findSearchOrderList(Integer loggedMemberID, SearchDto searchDto) {
        String category = searchDto.getCategory();
        String keyword = searchDto.getKeyword();
        LocalDate startDate = searchDto.getStartDate();
        LocalDate endDate = searchDto.getEndDate();

        List<Object[]> ObjectSearchOrderList = mypageDao.findSearchOrderList(
                loggedMemberID,
                category,
                keyword,
                startDate,
                endDate);

        //Object타입을 MypageOrderListDto타입으로 변환시켜 리스트화
        List<MypageOrderListDto> mypageSearchOrderListDtos = ObjectSearchOrderList.stream()
                .map(index -> new MypageOrderListDto(
                        ((Number) index[0]).intValue(),
                        (String) index[1],
                        ((Timestamp) index[2]).toLocalDateTime(),
                        (String) index[3],
                        ((Number) index[4]).intValue(),
                        (String) index[5]
                ))
                .collect(Collectors.toList());

        return mypageSearchOrderListDtos;
    }


    public void updateInfo(MemberDto memberDto) {
        Member member = memberDto.toMember();
        mypageDao.save(member);
    }

    public int deleteAccount(Integer id) {
        return mypageDao.deleteAccount(id);
    }

    public List<MypageSubscribeDto> findSubscribe(Integer loggedMemberID) {
        List<Object[]> ObjectMypageSubscribe = mypageDao.findSubscribe(loggedMemberID);

        List<MypageSubscribeDto> mypageSubscribeDtoList = ObjectMypageSubscribe.stream()
                .map(index -> new MypageSubscribeDto(
                        (String) index[0],
                        ((Timestamp) index[1]).toLocalDateTime().toLocalDate(),
                        ((Timestamp) index[2]).toLocalDateTime().toLocalDate(),
                        (String) index[3],
                        (String) index[4],
                        ((Number) index[5]).intValue()
                ))
                .collect(Collectors.toList());
        return mypageSubscribeDtoList;
    }
}
