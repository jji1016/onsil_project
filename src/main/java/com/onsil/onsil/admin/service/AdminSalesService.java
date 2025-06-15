package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminSalesDao;
import com.onsil.onsil.admin.dto.AdminSalesDashboardDto;
import com.onsil.onsil.admin.dto.AdminSalesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSalesService {
    private final AdminSalesDao adminSalesDao;

    public List<AdminSalesDto> getPeriodSales(String type, String startDate, String endDate) {
        List<Object[]> results;
        switch (type) {
            case "week":
                results = adminSalesDao.getWeeklySales(startDate, endDate);
                break;
            case "month":
                results = adminSalesDao.getMonthlySales(startDate, endDate);
                break;
            case "day":
            default:
                results = adminSalesDao.getDailySales(startDate, endDate);
        }
        return results.stream()
                .map(obj -> AdminSalesDto.builder()
                        .label((String)obj[0])
                        .amount(String.valueOf(obj[1] != null ? ((Number)obj[1]).longValue() : 0L))
                        .build())
                .collect(Collectors.toList());
    }

    public List<AdminSalesDto> getCategorySales(String startDate, String endDate) {
        return adminSalesDao.getCategorySales(startDate, endDate).stream()
                .map(obj -> AdminSalesDto.builder()
                        .label((String)obj[0])
                        .amount(String.valueOf(obj[1] != null ? ((Number)obj[1]).longValue() : 0L))
                        .build())
                .collect(Collectors.toList());
    }

    public AdminSalesDashboardDto getSalesDashboard(String type, String startDate, String endDate) {
        List<AdminSalesDto> periodSales = getPeriodSales(type, startDate, endDate);
        List<AdminSalesDto> categorySales = getCategorySales(startDate, endDate);
        return AdminSalesDashboardDto.builder()
                .periodSales(periodSales)
                .categorySales(categorySales)
                .build();
    }
}

