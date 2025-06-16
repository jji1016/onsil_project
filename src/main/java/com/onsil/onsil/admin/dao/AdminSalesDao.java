package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminSalesDao {
    private final AdminSalesRepository adminSalesRepository;

    public List<Object[]> getDailySales() {
        return adminSalesRepository.findDailySales();
    }
    public List<Object[]> getMonthlySales() {
        return adminSalesRepository.findMonthlySales();
    }
    public List<Object[]> getYearlySales() {
        return adminSalesRepository.findYearlySales();
    }
    public List<Object[]> getCategorySales() {
        return adminSalesRepository.findCategorySales();
    }
}
