package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminDao;
import com.onsil.onsil.admin.dto.*;
import com.onsil.onsil.admin.repository.AdminMemberRepository;
import com.onsil.onsil.constant.Role;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.mypage.dto.MypageOrderListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminDao adminDao;
    private final AdminMemberRepository memberRepository;

    // 멤버리스트 전체
    public List<MemberDto> getAllMembers() {
        return adminDao.getAllMembers().stream()
                .filter(member -> member.getRole() == Role.ROLE_USER && !member.isDeleteStatus())
                .map(member ->
                        MemberDto.builder()
                                .zipcode(member.getZipcode())
                                .address01(member.getAddress01())
                                .address02(member.getAddress02())
                                .nickName(member.getNickName())
                                .tel(member.getTel())
                                .userID(member.getUserID())
                                .userName(member.getUserName())
                                .userEmail(member.getUserEmail())
                                .regDate(member.getRegdate())
                                .build())
                .toList();
    }

    // 멤버 휴면전환
    public MemberDto deleteByUserID(String userID) {
        Member deleteUser = adminDao.findByUserID(userID);
        deleteUser.markAsDeleted(); // 삭제 상태로 변경
        memberRepository.save(deleteUser); // 상태 업데이트

        MemberDto memberDto = new MemberDto();
        memberDto.setDeleteStatus(true);
        return memberDto;
    }

    // userID로 memberDto 리턴
    public MemberDto findByUserID(String userID) {

        Member member = adminDao.findByUserID(userID);
        log.info("member={}", member.toString());
        MemberDto dto = new MemberDto();

        dto.setId(member.getId());
        dto.setUserID(member.getUserID());
        dto.setUserName(member.getUserName());
        dto.setUserEmail(member.getUserEmail());
        dto.setNickName(member.getNickName());
        dto.setTel(member.getTel());
        dto.setAddress01(member.getAddress01());
        dto.setAddress02(member.getAddress02());
        dto.setZipcode(member.getZipcode());
        dto.setRegDate(member.getRegdate());

        return dto;
    }

    // 구독정보 전체
    public List<SubscribeDto> getAllLists() {
        return adminDao.getAllLists().stream()
                .map(subscribe -> SubscribeDto.builder()
                        .userID(subscribe.getMember().getUserID())
                        .productName(subscribe.getProduct().getFlowerName())
                        .orderNumber("250604160511")
                        .startDate(subscribe.getStartDate())
                        .productPrice(subscribe.getProduct().getPrice())
                        .period(subscribe.getPeriod().name())
                        .build())
                .toList();
    }

    // 주문내역 전체
    public List<AdminOrderListDto> getAllOrderLists() {
        return adminDao.getAllOrderLists().stream()
                .map(subscribe -> AdminOrderListDto.builder()
                        .userID(subscribe.getMember().getUserID())
                        .flowerName(subscribe.getProduct().getFlowerName())
                        .orderTime(subscribe.getOrderTime())
                        .orderNum("250604160511")
                        .price(subscribe.getProduct().getPrice())
                        .quantity(subscribe.getQuantity())
                        .status(subscribe.getStatus().toString())
                        .build())
                .toList();
    }

    // 멤버 검색
    public List<MemberDto> search(String keyword, String category, LocalDateTime startDate, LocalDateTime endDate) {
        List<Member> members = adminDao.searchMembers(); // 전체 가져옴

        return members.stream()
                .filter(m -> {
                    if (keyword != null && !keyword.isBlank()) {
                        return switch (category) {
                            case "userID" -> m.getUserID().contains(keyword);
                            case "userName" -> m.getUserName().contains(keyword);
                            case "nickName" -> m.getNickName().contains(keyword);
                            default -> true;
                        };
                    }
                    return true;
                })
                .filter(m -> startDate == null || !m.getRegdate().isBefore(startDate))
                .filter(m -> endDate == null || !m.getRegdate().isAfter(endDate))
                .filter(m -> m.getRole() == Role.ROLE_USER) // 또는 m.getRole() != Role.ROLE_ADMIN
                .map(m -> MemberDto.builder()
                        .zipcode(m.getZipcode())
                        .address01(m.getAddress01())
                        .address02(m.getAddress02())
                        .nickName(m.getNickName())
                        .tel(m.getTel())
                        .userID(m.getUserID())
                        .userName(m.getUserName())
                        .userEmail(m.getUserEmail())
                        .regDate(m.getRegdate())
                        .role(m.getRole().name())
                        .build())
                .toList();
    }

    // 멤버 수정
    @Transactional
    public void modifyMember(String userID, MemberDto dto) {
        Member member = adminDao.findByUserID(userID);

        member.updateInfo(
                dto.getNickName(),
                dto.getUserEmail(),
                dto.getZipcode(),
                dto.getAddress01(),
                dto.getAddress02()
        );
    }

    // 총 멤버 수
    public int countAllMembers() {
        return adminDao.countAllMembers();
    }

    // 한달 이내 구독수.
    public int countOneMonth() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        return adminDao.countOneMonth(oneMonthAgo);
    }

    // 인기 구독아이템
    public List<PopularCountDto> popularSubscribe() {
        return adminDao.getPopularProducts();
    }

    // 한달이내 구독자 총 정보
    public SubscribeSumDto subscribeInOneMonth() {

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
//        Pageable topFive = PageRequest.of(0, 5);

        List<Subscribe> subscribes = adminDao.findRecentInMonth(oneMonthAgo);

        List<SubscribeDto> dtos = subscribes.stream().map(s -> SubscribeDto.builder()
                .id(s.getId())
                .userID(s.getMember().getUserID())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .orderNumber("ORD-" + s.getId())
                .productName(s.getProduct().getFlowerName())
                .productPrice(s.getProduct().getPrice())
                .period(s.getPeriod().name())
                .build()
        ).toList();

        int totalPrice = dtos.stream()
                .mapToInt(SubscribeDto::getProductPrice)
                .sum();

        return new SubscribeSumDto(dtos, totalPrice);
    }

    // 오늘 구독자
    public SubscribeSumDto subscribeToday() {

        LocalDateTime today = LocalDateTime.now().minusDays(1);
//        Pageable topFive = PageRequest.of(0, 5);

        List<Subscribe> subscribes = adminDao.findRecentInMonth(today);

        List<SubscribeDto> dtos = subscribes.stream().map(s -> SubscribeDto.builder()
                .id(s.getId())
                .userID(s.getMember().getUserID())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .orderNumber("ORD-" + s.getId())
                .productName(s.getProduct().getFlowerName())
                .productPrice(s.getProduct().getPrice())
                .period(s.getPeriod().name())
                .build()
        ).toList();

        int totalPrice = dtos.stream()
                .mapToInt(SubscribeDto::getProductPrice)
                .sum();

        return new SubscribeSumDto(dtos, totalPrice);
    }

    // 일주일 내 새로생긴 리뷰
    public int inOneWeekReview() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        return adminDao.inOneWeekReview(oneWeekAgo);
    }

    // 배송정보
    public DeliveryStatusDto getDeliveryStatusSummary() {
        return adminDao.countDeliveryStatuses();
    }

    // 한달내 판매?
    public List<SalesByMonthDto> getMonthlySales() {
        return adminDao.findMonthlySales();
    }

    // Service
    public Map<String, BigDecimal> getMergedMonthlyRevenue() {
        List<Object[]> orderTotal = adminDao.getMonthlyOrderRevenue();
        List<Object[]> subscribeTotal = adminDao.getMonthlySubscribeRevenue();

        Map<String, BigDecimal> result = new TreeMap<>();

        for (Object[] row : orderTotal) {
            String month = (String) row[0];
            BigDecimal price = (BigDecimal) row[1];
            result.put(month, price);
        }

        for (Object[] row : subscribeTotal) {
            String month = (String) row[0];
            BigDecimal price = (BigDecimal) row[1];
            result.merge(month, price, BigDecimal::add);
        }

        return result;
    }

}
