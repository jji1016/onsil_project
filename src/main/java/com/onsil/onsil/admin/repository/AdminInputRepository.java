package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdminInputRepository extends JpaRepository<Input, Integer> {
    List<Input> findByProductNameContaining(String keyword);
}
