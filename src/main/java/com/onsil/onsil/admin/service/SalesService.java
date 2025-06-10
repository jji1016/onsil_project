package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.SalesDao;
import com.onsil.onsil.admin.dto.CategorySalesDto;
import com.onsil.onsil.admin.dto.DailySalesDto;
import com.onsil.onsil.admin.dto.SalesSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesDao salesDao;

    public SalesSummaryDto getSalesSummary() {
        return SalesSummaryDto.builder()
                .todaySales(salesDao.getTodaySales())
                .monthSales(salesDao.getMonthSales())
                .monthOrderCount(salesDao.getMonthOrderCount())
                .avgOrderValue(salesDao.getMonthAvgOrderValue())
                .build();
    }

    public List<DailySalesDto> getDailySales() {
        return salesDao.getDailySales().stream()
                .map(obj -> DailySalesDto.builder()
                        .date(((java.sql.Timestamp)obj[0]).toLocalDateTime().toLocalDate())
                        .sales((BigDecimal)obj[1])
                        .build())
                .collect(Collectors.toList());
    }

    public List<CategorySalesDto> getCategorySales() {
        return salesDao.getCategorySales().stream()
                .map(obj -> CategorySalesDto.builder()
                        .category((String)obj[0])
                        .sales((BigDecimal)obj[1])
                        .build())
                .collect(Collectors.toList());
    }
}
