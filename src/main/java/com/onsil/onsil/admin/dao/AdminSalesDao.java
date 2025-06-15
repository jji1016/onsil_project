package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminSalesDao {
    private final AdminSalesRepository adminSalesRepository;

    public List<Object[]> getDailySales(String startDate, String endDate) {
        return adminSalesRepository.findDailySales(startDate, endDate);
    }
    public List<Object[]> getWeeklySales(String startDate, String endDate) {
        return adminSalesRepository.findWeeklySales(startDate, endDate);
    }
    public List<Object[]> getMonthlySales(String startDate, String endDate) {
        return adminSalesRepository.findMonthlySales(startDate, endDate);
    }
    public List<Object[]> getCategorySales(String startDate, String endDate) {
        return adminSalesRepository.findCategorySales(startDate, endDate);
    }
}
