package com.onsil.onsil.product.repository;

import com.onsil.onsil.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}