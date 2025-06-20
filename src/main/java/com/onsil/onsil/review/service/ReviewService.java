package com.onsil.onsil.review.service;

import com.onsil.onsil.communal.dto.CustomUserDetails;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Review;
import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.member.repository.MemberRepository;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.review.repository.ReviewRepository;
import com.onsil.onsil.subscribe.repository.SubscribeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final SubscribeRepository subscribeRepository;

    @Value("${file.path}reviews/")
    String reviewsPath;

    public void writeReview(Integer productId, Integer subscribeId, int rating, String content, MultipartFile imageFile) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new RuntimeException("인증된 사용자가 아닙니다.");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String loginId = customUserDetails.getUsername();

        Member member = memberRepository.findByUserID(loginId)
                .orElseThrow(() -> new RuntimeException("로그인한 회원 정보를 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Subscribe subscribe = null;
        if (subscribeId != null) {
            subscribe = subscribeRepository.findById(subscribeId)
                    .orElseThrow(() -> new RuntimeException("구독 정보를 찾을 수 없습니다."));
        }

        // 이미 리뷰 작성 여부 확인
        boolean exists = reviewRepository.existsByMemberAndProduct(member, product);
        if (exists) {
            throw new RuntimeException("이미 작성한 리뷰가 있습니다.");
        }

        Review review = new Review();
        review.setProduct(product);
        review.setSubscribe(subscribe); // null 가능
        review.setMember(member);
        review.setRating(rating);
        review.setContent(content);

        if (imageFile != null && !imageFile.isEmpty()) {
            String originalFilename = imageFile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String storedFileName = baseName + "_" + timestamp + extension;

            File saveDir = new File(reviewsPath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            imageFile.transferTo(new File(reviewsPath + storedFileName));
            review.setImage(storedFileName);
        }

        reviewRepository.save(review);
    }

}