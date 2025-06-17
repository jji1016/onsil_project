package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.dto.DeliveryStatusDto;
import com.onsil.onsil.admin.dto.PopularCountDto;
import com.onsil.onsil.admin.dto.SalesByMonthDto;
import com.onsil.onsil.admin.repository.AdminMemberRepository;
import com.onsil.onsil.admin.repository.AdminOrderListRepository;
import com.onsil.onsil.admin.repository.AdminSubscribeRepository;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.product.repository.ReviewRepository;
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
    private final ReviewRepository reviewRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member findByUserID(String userID) {
        return memberRepository.findByUserID(userID);
    }

    public List<Subscribe> findByMemberID(int id) {
        return subscribeRepository.findByMember_id(id);
    }

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

    public List<Subscribe> findRecentInMonth(LocalDateTime oneMonthAgo) {
        return  subscribeRepository.findRecentInMonth(oneMonthAgo);
    }

    public int inOneWeekReview(LocalDateTime oneWeekAgo) {
        return reviewRepository.countOneWeekReview(oneWeekAgo);
    }

    public DeliveryStatusDto countDeliveryStatuses() {
        return orderListRepository.countDeliveryStatuses();
    }

    public List<SalesByMonthDto> findMonthlySales() {
        return subscribeRepository.findMonthlySales();
    }

//    public List<OrderList> getAllOrderLists() {
//        return orderListRepository.findAll();
//    }
//
//    public List<Subscribe> getAllLists() {
//        return subscribeRepository.findAll();
//    }

    public List<Subscribe> getAllLists() {
        return subscribeRepository.findAllWithMemberAndProduct(); // JPQL fetch join
    }

    public List<OrderList> getAllOrderLists() {
        return orderListRepository.findAllWithMemberAndProduct(); // JPQL fetch join
    }


}
