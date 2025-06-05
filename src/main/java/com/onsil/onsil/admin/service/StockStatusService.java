package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dto.StockStatusDto;
import com.onsil.onsil.admin.repository.StockStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockStatusService {

    private final StockStatusRepository stockStatusRepository;

    public StockStatusService(StockStatusRepository stockStatusRepository) {
        this.stockStatusRepository = stockStatusRepository;
    }

    public List<StockStatusDto> getStockStatus() {
        return stockStatusRepository.findAllStockStatus();
    }
}
