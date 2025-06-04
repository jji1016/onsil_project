package com.onsil.onsil.cart.repository;

import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {}
