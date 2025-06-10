package com.onsil.onsil.product.service;

import com.onsil.onsil.communal.dto.CustomUserDetails;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Review;
import com.onsil.onsil.member.repository.MemberRepository;
import com.onsil.onsil.product.dto.ReviewDto;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.product.repository.ReviewRepository;
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

    @Value("${file.path}reviews/")
    String reviewsPath;

    public void writeReview(int productId, int rating, String content, MultipartFile imageFile) throws IOException {

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

        boolean exists = reviewRepository.existsByMemberAndProduct(member, product);
        if (exists) {
            throw new RuntimeException("이미 작성한 리뷰가 있습니다.");
        }

        Review review = new Review();
        review.setProduct(product);
        review.setMember(member);
        review.setRating(rating);
        review.setContent(content);

        if (imageFile != null && !imageFile.isEmpty()) {
            String originalFilename = imageFile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String storedFileName = baseName + "_" + timestamp + extension;

            String savePath = reviewsPath + storedFileName;

            File saveDir = new File(reviewsPath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            imageFile.transferTo(new File(savePath));
            review.setImage(storedFileName);
        }

        reviewRepository.save(review);
    }

    @Transactional
    public void delete(int reviewId) throws IllegalAccessException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        String imageFileName = review.getImage();
        if (imageFileName != null && !imageFileName.isEmpty()) {
            File file = new File(reviewsPath + imageFileName);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.err.println("리뷰 이미지 파일 삭제 실패: " + file.getAbsolutePath());
                }
            }
        }

        reviewRepository.delete(review);

    }


    public void updateReview(ReviewDto reviewDto, MultipartFile imageFile) throws IOException {
        Review review = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));

        review.setRating(reviewDto.getRating());
        review.setContent(reviewDto.getContent());

        if (imageFile != null && !imageFile.isEmpty()) {

            String oldImage = review.getImage();
            if (oldImage != null && !oldImage.isEmpty()) {
                File oldFile = new File(reviewsPath + oldImage);
                if (oldFile.exists()) {
                    boolean deleted = oldFile.delete();
                    if (!deleted) {
                        System.err.println("기존 이미지 삭제 실패: " + oldImage);
                    }
                }
            }

            String originalFilename = imageFile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String storedFileName = baseName + "_" + timestamp + extension;

            String savePath = reviewsPath + storedFileName;
            File saveDir = new File(reviewsPath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            try {
                imageFile.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new RuntimeException("리뷰 이미지 저장 중 오류 발생", e);
            }

            review.setImage(storedFileName);
        }

        reviewRepository.save(review);
    }

    public ReviewDto findMyReview(String loginId, int id) {
        Member member = memberRepository.findByUserID(loginId)
                .orElse(null);
        if (member == null) return null;

        Product product = productRepository.findById(id)
                .orElse(null);
        if (product == null) return null;

        return reviewRepository.findByMemberAndProduct(member, product)
                .map(ReviewDto::fromEntity)
                .orElse(null);
    }
}