package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.dto.DeliveryStatusDto;
import com.onsil.onsil.admin.dto.PopularCountDto;
import com.onsil.onsil.admin.dto.SalesByMonthDto;
import com.onsil.onsil.admin.repository.AdminMemberRepository;
import com.onsil.onsil.admin.repository.AdminOrderListRepository;
import com.onsil.onsil.admin.repository.AdminProductRepository;
import com.onsil.onsil.admin.repository.AdminSubscribeRepository;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.OrderList;
import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminDao {

    private final AdminMemberRepository memberRepository;
    private final AdminOrderListRepository orderListRepository;
    private final AdminSubscribeRepository subscribeRepository;
    private final com.onsil.onsil.review.repository.ReviewRepository reviewRepository;
    private final AdminProductRepository productRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
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

    public List<PopularCountDto> getPopularProducts() {
        List<Object[]> rows = productRepository.findPopularProducts();
        return rows.stream()
                .map(row -> new PopularCountDto(
                        ((Number) row[0]).intValue(),    // productId
                        (String) row[1],                 // flowerName
                        ((Number) row[2]).longValue()    // totalCount
                ))
                .toList();
    }


    public List<Subscribe> findRecentInMonth(LocalDateTime oneMonthAgo) {
        return subscribeRepository.findRecentInMonth(oneMonthAgo);
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

    public List<Subscribe> getAllLists() {
        return subscribeRepository.findAllWithMemberAndProduct(); // JPQL fetch join
    }

    public List<OrderList> getAllOrderLists() {
        return orderListRepository.findAllWithMemberAndProduct(); // JPQL fetch join
    }

    public List<Object[]> getMonthlyOrderRevenue() {
        return orderListRepository.getMonthlyOrderRevenue();
    }

    public List<Object[]> getMonthlySubscribeRevenue() {
        return subscribeRepository.getMonthlySubscribeRevenue();
    }


}
