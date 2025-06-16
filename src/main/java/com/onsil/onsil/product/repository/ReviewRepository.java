package com.onsil.onsil.product.repository;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByMemberAndProduct(Member member, Product product);

}