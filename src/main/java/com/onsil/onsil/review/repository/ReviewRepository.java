package com.onsil.onsil.review.repository;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Review;
import com.onsil.onsil.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByMemberAndProduct(Member member, Product product);

    @Query("SELECT count(R) FROM Review R WHERE R.regDate >= :oneWeekAgo")
    int countOneWeekReview(LocalDateTime oneWeekAgo);

//    @Query("SELECT COUNT(DISTINCT s.member.id) FROM Subscribe s WHERE s.startDate >= :todayDate")
//    int countOneMonthMember(@Param("todayDate") LocalDateTime todayDate);

    List<Review> findAllBySubscribe(Subscribe subscribe);

    List<Review> findAllByProduct(Product product);
}