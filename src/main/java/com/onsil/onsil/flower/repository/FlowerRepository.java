// src/main/java/com/onsil/onsil/flower/repository/FlowerRepository.java
package com.onsil.onsil.flower.repository;

import com.onsil.onsil.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {

    List<Flower> findByfMonth(Integer fMonth);

    Optional<Flower> findByDataNo(Integer dataNo);
}
