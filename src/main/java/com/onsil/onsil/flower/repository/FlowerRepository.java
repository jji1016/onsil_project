package com.onsil.onsil.flower.repository;

import com.onsil.onsil.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {
    List<Flower> findByfMonth(Integer fMonth);
}
