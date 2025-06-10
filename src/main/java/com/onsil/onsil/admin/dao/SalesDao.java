package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SalesDao {
    private final SalesRepository salesRepository;

    public java.math.BigDecimal getTodaySales() { return salesRepository.getTodaySales(); }
    public java.math.BigDecimal getMonthSales() { return salesRepository.getMonthSales(); }
    public Long getMonthOrderCount() { return salesRepository.getMonthOrderCount(); }
    public java.math.BigDecimal getMonthAvgOrderValue() { return salesRepository.getMonthAvgOrderValue(); }
    public List<Object[]> getDailySales() { return salesRepository.getDailySales(); }
    public List<Object[]> getCategorySales() { return salesRepository.getCategorySales(); }
}
