package com.onsil.onsil.product.repository;

import com.onsil.onsil.entity.SubMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubMaterialsRepository extends JpaRepository<SubMaterials, Integer> {
}
