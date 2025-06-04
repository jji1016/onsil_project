package com.onsil.onsil.mypage;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.member.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
}
