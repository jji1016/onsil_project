package com.onsil.onsil.product.service;

import com.onsil.onsil.communal.dto.CustomUserDetails;  // CustomUserDetails 임포트 추가
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Review;
import com.onsil.onsil.member.repository.MemberRepository;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.product.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public void writeReview(int productId, int rating, String content) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new RuntimeException("인증된 사용자가 아닙니다.");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String loginId = customUserDetails.getUsername();  // getUserID(), getId() 등 필요에 맞게 변경 가능

        Member member = memberRepository.findByUserID(loginId)
                .orElseThrow(() -> new RuntimeException("로그인한 회원 정보를 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Review review = new Review();
        review.setProduct(product);
        review.setMember(member);
        review.setRating(rating);
        review.setContent(content);
        reviewRepository.save(review);
    }
}