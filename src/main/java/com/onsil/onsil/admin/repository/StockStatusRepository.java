package com.onsil.onsil.admin.repository;

import com.onsil.onsil.admin.dto.StockStatusDto;
import com.onsil.onsil.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockStatusRepository extends JpaRepository<Stock, Integer> {
    List<StockStatusDto> findAllStockStatus();
}
