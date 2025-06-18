package com.onsil.onsil.flower.repository;

import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowerRepository extends JpaRepository<Product, Integer> {

    // 월별 꽃 정보 조회
    @Query("SELECT p FROM Product p WHERE p.fMonth = :month ORDER BY p.id")
    List<Product> findByFMonth(@Param("month") Integer month);

    // 전체 꽃 정보 조회 (월별 정렬)
    @Query("SELECT p FROM Product p WHERE p.fMonth IS NOT NULL ORDER BY p.fMonth, p.id")
    List<Product> findAllByFMonthOrderByFMonth();

    // 특정 월에 등록된 꽃 개수 조회
    @Query("SELECT COUNT(p) FROM Product p WHERE p.fMonth = :month")
    Integer countByFMonth(@Param("month") Integer month);

    // 꽃 이름으로 검색
    @Query("SELECT p FROM Product p WHERE p.flowerName LIKE %:keyword% ORDER BY p.flowerName")
    List<Product> findByFlowerNameContaining(@Param("keyword") String keyword);

    // 꽃말로 검색
    @Query("SELECT p FROM Product p WHERE p.flowLang LIKE %:keyword% ORDER BY p.flowerName")
    List<Product> findByFlowLangContaining(@Param("keyword") String keyword);

    // 꽃 이름 또는 꽃말로 검색
    @Query("SELECT p FROM Product p WHERE p.flowerName LIKE %:keyword% OR p.flowLang LIKE %:keyword% ORDER BY p.flowerName")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    // productId로 상세 정보 조회
    Optional<Product> findById(Integer productId);
}
