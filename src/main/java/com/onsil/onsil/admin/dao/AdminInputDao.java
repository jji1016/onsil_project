package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminInputRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminInputDao {
    private final AdminInputRepository adminInputRepository;

    public List<Object[]> searchInputs(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        return adminInputRepository.searchInputs(category, keyword, startDate, endDate);
    }
}